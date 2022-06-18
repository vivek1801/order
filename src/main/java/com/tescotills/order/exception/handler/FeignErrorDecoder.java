package com.tescotills.order.exception.handler;

import static feign.FeignException.errorStatus;

import org.springframework.stereotype.Component;

import com.tescotills.order.exception.ProductNotFoundException;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
			Exception ex = null;
	      FeignException exception = errorStatus(methodKey, response);
	      
        if (exception == null) {
            // Unknown error, returning FeignException
            log.warn("Received error {} from {} response. error is null or unknown, returning FeignException ...",
                response.status(), methodKey);
            ex = new Exception();
        } else {
        	
        	if(exception.status()==404) {
        		ex = new ProductNotFoundException("ERROR1000-PS", "Product not found");
        	}
        }
        return ex;

    }

	private Error readError(Response response) {
		return null;
	}

}
