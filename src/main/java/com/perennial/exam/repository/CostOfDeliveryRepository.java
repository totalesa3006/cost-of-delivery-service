package com.perennial.exam.repository;

import com.perennial.exam.entity.CostOfDelivery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostOfDeliveryRepository extends JpaRepository<CostOfDelivery,Integer> {
  public List<CostOfDelivery> findAllByOrderByPriorityAsc();
}
