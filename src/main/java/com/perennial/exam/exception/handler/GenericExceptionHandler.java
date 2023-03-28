package com.perennial.exam.exception.handler;

import static com.perennial.exam.constant.ExceptionConstants.ERROR_CODE_1001;
import static com.perennial.exam.constant.ExceptionConstants.ERROR_CODE_1003;
import static com.perennial.exam.constant.ExceptionConstants.ERROR_CODE_1004;
import static com.perennial.exam.constant.ExceptionConstants.GENERIC_ERROR_MSG;
import static com.perennial.exam.constant.ExceptionConstants.SERVER_ERROR;

import com.perennial.exam.beans.BaseResponse;
import com.perennial.exam.beans.CostOfDeliveryErrorResponse;
import com.perennial.exam.beans.ErrorResponse;
import com.perennial.exam.exception.InvalidDataException;
import com.perennial.exam.exception.ServiceException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@SuppressWarnings({"unchecked", "rawtypes"})
@Log4j2
@RestControllerAdvice
public class GenericExceptionHandler {


  @ExceptionHandler(InvalidDataException.class)
  public final ResponseEntity<BaseResponse> handleInvalidRequestException(
      InvalidDataException ex, WebRequest request) {
    ErrorResponse lErrorResponse =
        new CostOfDeliveryErrorResponse(
            ERROR_CODE_1001, ex.getMessage());
    return ResponseEntity.badRequest().body(lErrorResponse);
  }

  @ExceptionHandler(ServiceException.class)
  public final ResponseEntity<BaseResponse> handleServiceException(
      ServiceException ex, WebRequest request) {
    CostOfDeliveryErrorResponse lErrorResponse =
        new CostOfDeliveryErrorResponse(
            ERROR_CODE_1003, ex.getMessage());
    return ResponseEntity.badRequest().body(lErrorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<BaseResponse> handleValidationException(
      MethodArgumentNotValidException ex, WebRequest request) {

    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

    CostOfDeliveryErrorResponse lErrorResponse =
        new CostOfDeliveryErrorResponse(
            ERROR_CODE_1004, String.join(",",errors));
    return ResponseEntity.badRequest().body(lErrorResponse);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<BaseResponse> handleAllExceptions(Exception ex, WebRequest request) {
    CostOfDeliveryErrorResponse lErrorResponse = new CostOfDeliveryErrorResponse(SERVER_ERROR, GENERIC_ERROR_MSG);
    return new ResponseEntity(lErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
