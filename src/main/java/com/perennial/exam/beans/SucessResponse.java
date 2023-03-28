package com.perennial.exam.beans;

public interface SucessResponse  extends  BaseResponse{
	String status = "SUCCESS";

	default String getStatus() {
		return status;
	}

}
