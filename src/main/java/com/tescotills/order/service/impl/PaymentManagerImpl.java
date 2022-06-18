package com.tescotills.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tescotills.order.adapter.PaymentClient;
import com.tescotills.order.model.CreditCard;
import com.tescotills.order.service.PaymentManager;

@Service
public class PaymentManagerImpl implements PaymentManager {

	@Autowired
	PaymentClient paymentClient;
	
	@Value("${order.payment.enabled}")
	boolean allowPayment;


	@Override
	public boolean doPayment(CreditCard card) {
		if(allowPayment) {
			return paymentClient.payment(card);	
		}
		return true;
	}
}
