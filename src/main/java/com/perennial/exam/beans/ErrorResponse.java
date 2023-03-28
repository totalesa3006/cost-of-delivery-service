package com.perennial.exam.beans;

import com.perennial.exam.beans.BaseResponse;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface ErrorResponse extends BaseResponse {

    String status ="ERROR";

   default String getStatus() {
    return status;
    }


}
