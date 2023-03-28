package com.perennial.exam.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.service.CostOfDeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CostOfDeliveryController.class)
class CostOfDeliveryControllerTest {

  @Mock
  private CostOfDeliveryService costOfDeliveryService;

  @InjectMocks
  private CostOfDeliveryController costOfDeliveryController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testGetCostOfDelivery()  throws Exception{
    CostOfDeliveryRequest pCostOfDeliveryRequest = CostOfDeliveryRequest.builder().height(20.0f)
        .width(10.0f).length(10.0f).weight(49.0f).voucherCode("MYNT").ignoreExpiredVoucherCode(true)
        .build();

    mockMvc.perform(post("/getCostOfDelivery").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pCostOfDeliveryRequest))).andDo(print());

  }
}