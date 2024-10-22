package com.zeotap.ruleengine.repository;

import com.zeotap.ruleengine.model.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
    RuleEntity findByRule(String rule);
}
