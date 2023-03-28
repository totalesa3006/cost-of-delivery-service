package com.perennial.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Sms Service Main Application Class.
 *
 * @author Shailesh Totale.
 */
@SpringBootApplication
@EnableFeignClients
public class CostOfDeliveryApplication {

  private static final Logger log = LoggerFactory.getLogger(CostOfDeliveryApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(CostOfDeliveryApplication.class, args);
    log.info("Cost Of Delivery Service started successfully.");
  }
}
