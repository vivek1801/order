package com.tescotills.order.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tescotills.order.exception.InvalidOrderException;
import com.tescotills.order.exception.OrderNotFoundException;
import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Order;
import com.tescotills.order.service.OrderManager;
import com.tescotills.order.service.OrderTools;

@Service
public class OrderManagerImpl implements OrderManager {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OrderTools orderTools;
	
	@Override
	public Order getOrderForCustomer(String customerid) {
		Order order = orderTools.createOrder(customerid);
		if(CollectionUtils.isEmpty(order.getItems())) {
			order.setItems(new ArrayList<>());
		}
		return order;
	}

	@Override
	public void persistOrder(Order order) {
		orderTools.persistOrder(order);
	}

	@Override
	public Order getOrder(String orderId, String customerid) throws InvalidOrderException {
		Order order = orderTools.findOrder(orderId);
		if(order != null && !order.getCustomerId().equals(customerid)) {
			throw new InvalidOrderException("ERR1003", "Invalid Order for user");
		}
		return order;
	}

	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private double calculateOrderTotalAmount(Order order) {
		return order.getItems().stream().map(s -> s.getPrice()).mapToDouble(Double::valueOf).sum();
	}

	@Override
	public Order updateItem(String customerId, String orderId, LineItems item) throws OrderNotFoundException {
		Order order = orderTools.findOrder(orderId);
		boolean isUpdated = false;
		if (customerId.equals(order.getCustomerId())) {
			List<LineItems> items = order.getItems();
			for (LineItems savedItem : items) {
				if (Long.compare(savedItem.getProductId(), item.getProductId()) == 0) {
					if (item.getQuantity() < savedItem.getBulkbuylimit()) {
						savedItem.setQuantity(item.getQuantity());
						savedItem.setPrice(item.getPrice());
						order.setTotalprice(calculateOrderTotalAmount(order));
						orderTools.update(order);
						isUpdated=true;
						break;
					} else {
						throw new RuntimeException("Bulkbuy restrictions violation");// FIXME:Custom exception
					}
				}
			}
			if(!isUpdated) {
				logger.error("No valid lineitem {} found for order {}", item, order);
				throw new IllegalArgumentException("No valid lineitem found for order");
			}
		}
		return order;
	}

	@Override
	public boolean deleteItems(String customerId, String orderId, String productId) {
		Order order = orderTools.findOrder(orderId);
		boolean isDeleted = false;
		if (customerId.equals(order.getCustomerId())) {
			for (Iterator<LineItems> iterator = order.getItems().iterator(); iterator.hasNext();) {
				LineItems item = iterator.next();
				if(Long.toString(item.getProductId()).equals(productId)) {
					iterator.remove();
					isDeleted = true;
				}
			}
			if(isDeleted) {
				order.setTotalprice(calculateOrderTotalAmount(order));
				orderTools.persistOrder(order);
			}
				
		}
		return isDeleted;
	}

}
