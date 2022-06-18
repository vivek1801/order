package com.tescotills.order.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tescotills.order.exception.Error;
import com.tescotills.order.exception.InvalidOrderException;
import com.tescotills.order.exception.InvalidPaymentDetailsException;
import com.tescotills.order.exception.OrderMaxItemLimitException;
import com.tescotills.order.exception.OrderNotFoundException;
import com.tescotills.order.exception.ProductNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(Exception.class)
    @ResponseBody
    private ResponseEntity<Error> handleApplicationException(final Exception exception) {
        log.error("Unexpected error", exception);
        Error error = new Error("ERR1000","Internal Server Error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    private ResponseEntity<Error> handleOrderNotFoundException(final OrderNotFoundException exception) {
        log.error("Order error", exception);
        Error error = new Error(exception.getErrorId(), exception.getErrorText());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderMaxItemLimitException.class)
    @ResponseBody
    private ResponseEntity<Error> handleOrderMaxItemLimitException(final OrderMaxItemLimitException exception) {
        log.error("Order error", exception);
        Error error = new Error(exception.getErrorId(), exception.getErrorText());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidOrderException.class)
    @ResponseBody
    private ResponseEntity<Error> handleInvalidOrderException(final InvalidOrderException exception) {
        log.error("Order error", exception);
        Error error = new Error(exception.getErrorId(), exception.getErrorText());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    private ResponseEntity<Error> handleOrderNotFoundException(final ProductNotFoundException exception) {
        log.error("Order error", exception);
        Error error = new Error(exception.getErrorId(), exception.getErrorText());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPaymentDetailsException.class)
    @ResponseBody
    private ResponseEntity<Error> handleInvalidPaymentDetailsException(final InvalidPaymentDetailsException exception) {
        log.error("Payment error", exception);
        Error error = new Error(exception.getErrorId(), exception.getErrorText());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
