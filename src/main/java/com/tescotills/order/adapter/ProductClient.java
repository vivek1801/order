package com.tescotills.order.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tescotills.order.model.Product;

@FeignClient(value = "orderservice", url = "${product.service.uri}")
public interface ProductClient {
	
	@RequestMapping(value = "1.0/products/{productid}", method = RequestMethod.GET)
	 Product getProduct(@PathVariable(value ="productid") final long productid);
}
