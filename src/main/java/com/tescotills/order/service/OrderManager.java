package com.tescotills.order.service;

import java.util.List;

import com.tescotills.order.exception.InvalidOrderException;
import com.tescotills.order.exception.OrderNotFoundException;
import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Order;

public interface OrderManager {

	List<Order> findAll();

	Order getOrder(String orderId, String customerid) throws InvalidOrderException;

	void persistOrder(Order order);

	Order getOrderForCustomer(String customerid);

	Order updateItem(String customerId, String orderId, LineItems item) throws OrderNotFoundException;

	boolean deleteItems(String customerId, String orderId, String productId);

}
