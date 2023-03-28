package com.perennial.exam.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cost_of_delivery")
public class CostOfDelivery {
  @Id
  private int priority;
  private String ruleName;
  private float minWeight;
  private float maxWeight;
  private float minVolume;
  private float maxVolume;
  private String sizeFactor;
  private float costFactor;


}
