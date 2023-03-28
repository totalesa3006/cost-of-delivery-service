package com.perennial.exam.ruleengine.factory;

import com.perennial.exam.entity.CostOfDelivery;
import com.perennial.exam.ruleengine.rule.CostOfDeliveryRule;
import com.perennial.exam.ruleengine.rule.GenericCostOfDeliveryRule;
import com.perennial.exam.ruleengine.rule.VolumeBasedCostOfDeliveryRule;
import com.perennial.exam.ruleengine.rule.WeightBasedCostOfDeliveryRule;

public class CostOfDeliveryRuleFactory {

  public static final String WEIGHT = "WEIGHT";
  public static final String VOLUME = "VOLUME";


  public static CostOfDeliveryRule getCostOfDeliveryRule(CostOfDelivery pCostOfDelivery) {

    switch (pCostOfDelivery.getSizeFactor()) {

      case WEIGHT:
        CostOfDeliveryRule weightBasedRule = WeightBasedCostOfDeliveryRule.builder()
            .ruleName(pCostOfDelivery.getRuleName()).costFactor(pCostOfDelivery.getCostFactor())
            .minWeight(pCostOfDelivery.getMinWeight()).maxWeight(pCostOfDelivery.getMaxWeight()).build();
          return weightBasedRule;
      case VOLUME:
        CostOfDeliveryRule volumeBasedRule = VolumeBasedCostOfDeliveryRule.builder()
            .ruleName(pCostOfDelivery.getRuleName()).costFactor(pCostOfDelivery.getCostFactor())
            .minVolume(pCostOfDelivery.getMinVolume()).maxVolume(pCostOfDelivery.getMaxVolume()).build();
        return volumeBasedRule;
      default:
        CostOfDeliveryRule geneircRule = GenericCostOfDeliveryRule.builder()
            .ruleName(pCostOfDelivery.getRuleName()).costFactor(pCostOfDelivery.getCostFactor())
            .minWeight(pCostOfDelivery.getMinWeight()).maxWeight(pCostOfDelivery.getMaxWeight())
            .minVolume(pCostOfDelivery.getMinVolume()).maxVolume(pCostOfDelivery.getMaxVolume()).build();
        return geneircRule;
    }

  }
}
