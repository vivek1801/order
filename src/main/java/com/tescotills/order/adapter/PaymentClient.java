package com.tescotills.order.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tescotills.order.model.CreditCard;

@FeignClient(value = "paymentservice", url = "${payment.service.uri}")
public interface PaymentClient {
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	boolean payment(@RequestBody final CreditCard card);
}
