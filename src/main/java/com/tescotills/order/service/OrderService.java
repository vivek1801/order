package com.tescotills.order.service;

import java.util.List;

import com.tescotills.order.exception.InvalidOrderException;
import com.tescotills.order.exception.InvalidPaymentDetailsException;
import com.tescotills.order.exception.OrderMaxItemLimitException;
import com.tescotills.order.exception.OrderNotFoundException;
import com.tescotills.order.exception.ProductNotFoundException;
import com.tescotills.order.model.CreditCard;
import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Order;

public interface OrderService {
	
	Order addItemsToOrder(String customerid, String orderid, List<LineItems> items) throws OrderNotFoundException, OrderMaxItemLimitException, InvalidOrderException, ProductNotFoundException;

	Order addItemsToNewOrder(String customerid, List<LineItems> items) throws OrderNotFoundException, OrderMaxItemLimitException, ProductNotFoundException;
	
	Order getOrderByOrderId(String customerid, String orderid) throws InvalidOrderException;
	
	List<Order> getOrders(String customerid) throws InvalidOrderException;

    Order updateItemInOrder(String customerId, String orderId, LineItems item) throws OrderNotFoundException;

    boolean deleteItemsInOrder(String customerId, String orderId, String productId);

	Order checkout(String customerId, String orderId, CreditCard card) throws InvalidOrderException, InvalidPaymentDetailsException;
}
