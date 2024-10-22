package com.zeotap.ruleengine;

import com.zeotap.ruleengine.exception.RuleValidationException;
import com.zeotap.ruleengine.model.Node;
import com.zeotap.ruleengine.service.RuleEngine;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RuleEngineTest {

    // Test creating and evaluating a valid rule
    @Test
    public void testCreateAndEvaluateRule() throws RuleValidationException {
        RuleEngine engine = new RuleEngine();
        // Rule to be tested: age > 30 AND department = 'Sales'
        Node rule = engine.createRuleWithValidation("age > 30 AND department = 'Sales'");
        
        // Test case where the rule should evaluate to true
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", 35);
        userData.put("department", "Sales");
        assertTrue(engine.evaluateRule(rule, userData), "Expected rule to evaluate to true for age 35 and department Sales");
        
        // Test case where the rule should evaluate to false (age mismatch)
        userData.put("age", 25);
        assertFalse(engine.evaluateRule(rule, userData), "Expected rule to evaluate to false for age 25");
        
        // Test case where the rule should evaluate to false (department mismatch)
        userData.put("age", 35);
        userData.put("department", "Marketing");
        assertFalse(engine.evaluateRule(rule, userData), "Expected rule to evaluate to false due to department mismatch");
    }

    // Test invalid rules and validation
    @Test
    public void testInvalidRule() {
        RuleEngine engine = new RuleEngine();
        
        // Test case for an empty rule, expecting a RuleValidationException
        assertThrows(RuleValidationException.class, () -> {
            engine.createRuleWithValidation("");
        }, "Expected RuleValidationException due to empty rule");
        
        // Test case for a rule with invalid syntax, expecting a RuleValidationException
        assertThrows(RuleValidationException.class, () -> {
            engine.createRuleWithValidation("invalidrule");
        }, "Expected RuleValidationException due to invalid syntax");
    }

    // Test handling a more complex rule with OR
    @Test
    public void testComplexRule() throws RuleValidationException {
        RuleEngine engine = new RuleEngine();
        // Rule to be tested: age > 30 OR department = 'HR'
        Node rule = engine.createRuleWithValidation("age > 30 OR department = 'HR'");
        
        // Test case where the rule should evaluate to true (age > 30)
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", 35);
        userData.put("department", "Sales");
        assertTrue(engine.evaluateRule(rule, userData), "Expected rule to evaluate to true due to age > 30");
        
        // Test case where the rule should evaluate to true (department is HR)
        userData.put("age", 25);
        userData.put("department", "HR");
        assertTrue(engine.evaluateRule(rule, userData), "Expected rule to evaluate to true due to department HR");
        
        // Test case where the rule should evaluate to false (age < 30 and department is not HR)
        userData.put("age", 25);
        userData.put("department", "Sales");
        assertFalse(engine.evaluateRule(rule, userData), "Expected rule to evaluate to false due to both conditions failing");
    }
}
