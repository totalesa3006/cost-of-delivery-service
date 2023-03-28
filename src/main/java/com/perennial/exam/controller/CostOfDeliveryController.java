package com.perennial.exam.controller;

import com.perennial.exam.beans.BaseResponse;
import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.beans.CostOfDeliveryResponse;
import com.perennial.exam.service.CostOfDeliveryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * CostOfDeliveryController- Controller class for Cost Of Delivery MicroServices
 */

/**
 * @author Shailesh Totale
 */
@RestController
public class CostOfDeliveryController {

  private static final Logger logger = LoggerFactory.getLogger(CostOfDeliveryController.class);

  private CostOfDeliveryService costOfDeliveryService;

  public CostOfDeliveryController(CostOfDeliveryService costOfDeliveryService
  ) {
    this.costOfDeliveryService = costOfDeliveryService;
  }

  @PostMapping("/getCostOfDelivery")
  public ResponseEntity<BaseResponse> getCostOfDelivery(
      @RequestBody @Valid CostOfDeliveryRequest pCostOfDeliveryRequest) {
    logger.info("Start getCostOfDelivery method");
    CostOfDeliveryResponse costOfDeliveryResponse = costOfDeliveryService.getCostOfDelivery(
        pCostOfDeliveryRequest);
    return ResponseEntity.ok(costOfDeliveryResponse);
  }
}
