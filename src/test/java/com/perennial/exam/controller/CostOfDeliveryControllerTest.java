package com.perennial.exam.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.config.ApplicationConfig;
import com.perennial.exam.service.CostOfDeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { ApplicationConfig.class})
class CostOfDeliveryControllerTest {

  @Mock
  private CostOfDeliveryService costOfDeliveryService;

  @InjectMocks
  private CostOfDeliveryController costOfDeliveryController;

  @Autowired
  private MockMvc mockMvc;


/*  @Autowired
  private MappingJackson2HttpMessageConverter springMvcJacksonConverter;*/

  @Test
  void testGetCostOfDelivery()  throws Exception{

    ObjectMapper objectMapper  = new ObjectMapper();
    CostOfDeliveryRequest pCostOfDeliveryRequest = CostOfDeliveryRequest.builder().height(20.0f)
        .width(10.0f).length(10.0f).weight(49.0f).voucherCode("MYNT").ignoreExpiredVoucherCode(true)
        .build();


    mockMvc.perform(post("/getCostOfDelivery").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pCostOfDeliveryRequest))).andDo(print());

  }
}