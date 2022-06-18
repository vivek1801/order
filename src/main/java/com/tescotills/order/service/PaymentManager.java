package com.tescotills.order.service;

import com.tescotills.order.model.CreditCard;

public interface PaymentManager {

	boolean doPayment(CreditCard card);


}
