package com.tescotills.order.service;

import java.util.List;

import com.tescotills.order.model.Order;

public interface OrderTools {
	
	Order createOrder(String customerid);

	void persistOrder(Order order);
	
	Order findOrder(String orderid);

	List<Order> findAll(String customerid);

	void update(Order order);

	void delete(String orderid);

	
	
}
