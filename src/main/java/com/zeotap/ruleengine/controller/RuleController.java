package com.zeotap.ruleengine.controller;

import com.zeotap.ruleengine.model.Node;
import com.zeotap.ruleengine.model.RuleEntity;
import com.zeotap.ruleengine.repository.RuleRepository;
import com.zeotap.ruleengine.service.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleEngine ruleEngine;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    public RuleController(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    // Endpoint to create a rule
    @PostMapping("/create")
    public Node createRule(@RequestParam String rule) {
        return ruleEngine.createRuleWithValidation(rule);
    }

    // Endpoint to evaluate a rule against user data
    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestParam String rule, @RequestBody Map<String, Object> data) {
        Node ruleNode = ruleEngine.createRuleWithValidation(rule);
        return ruleEngine.evaluateRule(ruleNode, data);
    }

    // Endpoint to save a rule in the database
    @PostMapping("/save")
    public RuleEntity saveRule(@RequestParam String rule) {
        RuleEntity entity = new RuleEntity();
        entity.setRule(rule);
        return ruleRepository.save(entity);
    }

    // Endpoint to update an existing rule
    @PutMapping("/update/{id}")
    public RuleEntity updateRule(@PathVariable Long id, @RequestParam String newRule) {
        RuleEntity ruleEntity = ruleRepository.findById(id).orElseThrow(() -> new RuntimeException("Rule not found"));
        ruleEntity.setRule(newRule);
        return ruleRepository.save(ruleEntity);
    }
}
