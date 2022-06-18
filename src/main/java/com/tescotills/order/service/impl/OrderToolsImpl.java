package com.tescotills.order.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tescotills.order.model.Order;
import com.tescotills.order.repository.OrderRepository;
import com.tescotills.order.service.OrderTools;

import lombok.Builder;

@Service
public class OrderToolsImpl implements OrderTools {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	public void setProductRepository(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}
	
	@Override
	@Builder
	public Order createOrder(String customerId) {
		Order order = Order.builder().customerId(customerId).orderId(getOrderId()).build();
		return order;
	}

	@Override
	public void persistOrder(Order order) {
		orderRepo.save(order);
	}

	@Override
	public void update(Order order) {
		orderRepo.save(order);		
	}

	@Override
	public void delete(String orderId) {
		orderRepo.delete(findOrder(orderId));		
	}
	
	@Override
	public Order findOrder(String orderId) {
		return orderRepo.findById(orderId).orElse(null);
	}

	@Override
	public List<Order> findAll(String customerId) {
		List<Order> orderList = new ArrayList<>();
		Iterator<Order> it = orderRepo.findAll().iterator();
		while (it.hasNext()) {
			orderList.add(it.next());
		}
		orderList.parallelStream().filter(o -> o.getCustomerId().equals(customerId));
		return orderList;
	}

	private String getOrderId() {
		long leftLimit = 1L;
	    long rightLimit = 10000000000L;
	    long id = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return String.valueOf(id);
	}

}
