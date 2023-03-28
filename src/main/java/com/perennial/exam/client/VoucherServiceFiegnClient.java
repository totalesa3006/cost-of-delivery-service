package com.perennial.exam.client;


import com.perennial.exam.beans.VoucherCodeResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * Voucher Service Feign Client.
 *
 * @author Shailesh Totale
 */
@FeignClient(name = "voucher-code-service", url = "${voucher.feign.url}")
public interface VoucherServiceFiegnClient {

	  @GetMapping(value = "/voucher/{voucherCode}?key={apiKey}")
	  @Headers("Content-Type: application/json")
	  public VoucherCodeResponse getVoucherDetails(@PathVariable("voucherCode") String vouchercode,@RequestParam String apiKey);

}
