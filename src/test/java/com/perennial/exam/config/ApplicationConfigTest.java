package com.perennial.exam.config;

import static com.perennial.exam.constant.CostOfDeliveryConstants.COST_OF_DELIVERY_RULES_MAP;
import static com.perennial.exam.constant.CostOfDeliveryConstants.COST_OF_DELIVERY_RULES_PRIORITY_MAP;
import static org.junit.jupiter.api.Assertions.*;

import com.perennial.exam.ruleengine.rule.CostOfDeliveryRule;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ApplicationConfigTest {

  @Bean(COST_OF_DELIVERY_RULES_MAP)
  public ConcurrentHashMap<String, CostOfDeliveryRule> costOfDeliveryRulesMap() {
    return new ConcurrentHashMap<>();
  }

  @Bean(COST_OF_DELIVERY_RULES_PRIORITY_MAP)
  public ConcurrentHashMap<String, List<String>> costOfDeliveryRulesPriorityList() {
    return new ConcurrentHashMap<>();
  }
}