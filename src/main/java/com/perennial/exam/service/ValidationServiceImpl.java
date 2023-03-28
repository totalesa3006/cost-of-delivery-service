package com.perennial.exam.service;

import com.perennial.exam.beans.CostOfDeliveryRequest;
import com.perennial.exam.constant.CostOfDeliveryConstants;
import com.perennial.exam.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
	private static final Logger log = LoggerFactory.getLogger(ValidationServiceImpl.class);

	@Override
	public void validateCostOfDeliveryRequest(CostOfDeliveryRequest pCostOfDeliveryRequest) {

	}

}