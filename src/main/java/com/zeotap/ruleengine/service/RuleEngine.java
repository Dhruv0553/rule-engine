package com.zeotap.ruleengine.service;

import com.zeotap.ruleengine.model.Node;
import com.zeotap.ruleengine.exception.RuleValidationException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuleEngine {

    // Create a rule with validation
    @Cacheable("rules")  // Caches the rule once created
    public Node createRuleWithValidation(String rule) {
        if (rule == null || rule.isEmpty()) {
            throw new RuleValidationException("Rule cannot be empty.");
        }
        if (!rule.matches(".*(AND|OR|>|<|=).*")) {
            throw new RuleValidationException("Invalid rule syntax.");
        }
        return createRule(rule);
    }

    // Method to create a rule (build the AST)
    public Node createRule(String rule) {
        if (rule.contains("AND")) {
            Node operatorNode = new Node("operator", "AND");
            operatorNode.setLeft(createRule(rule.substring(0, rule.indexOf("AND")).trim()));
            operatorNode.setRight(createRule(rule.substring(rule.indexOf("AND") + 3).trim()));
            return operatorNode;
        } else if (rule.contains("OR")) {
            Node operatorNode = new Node("operator", "OR");
            operatorNode.setLeft(createRule(rule.substring(0, rule.indexOf("OR")).trim()));
            operatorNode.setRight(createRule(rule.substring(rule.indexOf("OR") + 2).trim()));
            return operatorNode;
        } else {
            return new Node("operand", rule.trim());
        }
    }

    // Evaluate a rule against the user data
    public boolean evaluateRule(Node node, Map<String, Object> data) {
        if (node.getType().equals("operand")) {
            String[] condition = node.getValue().split(" ");
            String attribute = condition[0];
            String operator = condition[1];

            try {
                int value = Integer.parseInt(condition[2]);
                int userValue = (int) data.get(attribute);
                switch (operator) {
                    case ">": return userValue > value;
                    case "<": return userValue < value;
                    case "=": return userValue == value;
                    default: throw new RuleValidationException("Invalid operator");
                }
            } catch (NumberFormatException e) {
                String value = condition[2].replace("'", "");
                String userValue = (String) data.get(attribute);
                return userValue.equals(value);
            }
        } else {
            boolean leftEval = evaluateRule(node.getLeft(), data);
            boolean rightEval = evaluateRule(node.getRight(), data);
            return node.getValue().equals("AND") ? leftEval && rightEval : leftEval || rightEval;
        }
    }
}
