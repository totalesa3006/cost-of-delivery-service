package com.perennial.exam.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class VoucherCodeResponse {

  private String code;
  private float discount;
  @JsonFormat(pattern="yyyy-MM-dd")
  private Date expiry;
  private String error;


}
