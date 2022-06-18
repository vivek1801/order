package com.tescotills.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tescotills.order.constants.OrderStatus;
import com.tescotills.order.controller.OrderController;
import com.tescotills.order.exception.InvalidOrderException;
import com.tescotills.order.exception.InvalidPaymentDetailsException;
import com.tescotills.order.exception.OrderMaxItemLimitException;
import com.tescotills.order.exception.OrderNotFoundException;
import com.tescotills.order.exception.ProductNotFoundException;
import com.tescotills.order.model.CreditCard;
import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Order;
import com.tescotills.order.service.OrderManager;
import com.tescotills.order.service.OrderService;
import com.tescotills.order.service.OrderTools;
import com.tescotills.order.service.PaymentManager;
import com.tescotills.order.service.ProductManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderManager orderManager;

	@Autowired
	OrderTools orderTools;

	@Autowired
	ProductManager productManager;

	@Autowired
	PaymentManager paymentManager;

	@Override
	public Order addItemsToOrder(String customerid, String orderId, List<LineItems> items)
			throws OrderNotFoundException, OrderMaxItemLimitException, InvalidOrderException, ProductNotFoundException {
		
		Order order = orderManager.getOrder(orderId, customerid);
		 log.info(this.getClass().getName()+" :------ :"+"Order fetched from db : "+ order.toString());
		addItemsToOrderAndPersist(order, items);
		return order;
	}

	@Override
	public Order addItemsToNewOrder(String customerid, List<LineItems> items)
			throws OrderNotFoundException, OrderMaxItemLimitException, ProductNotFoundException {
		Order order = orderManager.getOrderForCustomer(customerid);
		addItemsToOrderAndPersist(order, items);
		return order;
	}

	private void addItemsToOrderAndPersist(Order order, List<LineItems> items)
			throws OrderNotFoundException, OrderMaxItemLimitException, ProductNotFoundException {
		if (order != null) {
			items.addAll(order.getItems());
			List<LineItems> pricedItems = getItemPrices(items);
			order.setItems(items);
			double totalPrice = pricedItems.parallelStream().collect(Collectors.summingDouble(p -> p.getPrice()));
			order.setTotalprice(totalPrice);
			order.setStatus(OrderStatus.CREATED.name());
			aggregateItems(order);
		} else {
			log.error("OrderNotFoundException");
			throw new OrderNotFoundException("ERR1001", "Order Not Found");
		}
		orderManager.persistOrder(order);
	}

	private void aggregateItems(Order order) throws OrderMaxItemLimitException {
		List<LineItems> orderItems = order.getItems();
		Map<Long, LineItems> itemsMap = new HashMap<>();

		for (LineItems i : orderItems) {
			LineItems item = null;
			if (itemsMap.containsKey(i.getProductId())) {
				item = itemsMap.get(i.getProductId());
				int qty = item.getQuantity() + i.getQuantity();
				double price = item.getPrice() + i.getPrice();
				item.setPrice(price);
				item.setQuantity(qty);
				itemsMap.put(i.getProductId(), item);
			} else {
				itemsMap.put(i.getProductId(), i);
			}
		}
		orderItems.clear();
		for(Map.Entry<Long, LineItems> i : itemsMap.entrySet()){
			LineItems item = i.getValue();
			if (item.getQuantity() > item.getBulkbuylimit()) {
				throw new OrderMaxItemLimitException("ERR1002", "Max Item crossed");
			}
			orderItems.add(item);
		}
		order.setItems(orderItems);
	}

	private List<LineItems> getItemPrices(List<LineItems> items) throws ProductNotFoundException {
		try {
			return productManager.getItemPrices(items);
		} catch(Exception ex) {
			 log.info(this.getClass().getName()+" :------ :ProductNotFoundException");
			throw new ProductNotFoundException("ERR1000-PS","Product Not Found");
		}
	}

	@Override
	public Order getOrderByOrderId(String customerId, String orderId) throws InvalidOrderException {
		return orderManager.getOrder(orderId, customerId);
	}

	@Override
	public List<Order> getOrders(String customerid) {
		return orderTools.findAll(customerid);
	}

    @Override
    public Order updateItemInOrder(String customerId, String orderId, LineItems item) throws OrderNotFoundException {
        Order order = orderManager.updateItem(customerId, orderId, item);
        return order;
    }

    @Override
    public boolean deleteItemsInOrder(String customerId, String orderId, String productId) {
        return orderManager.deleteItems(customerId, orderId, productId);
    }

	@Override
	public Order checkout(String customerId, String orderId, CreditCard card) throws InvalidOrderException, InvalidPaymentDetailsException {
		Order order = getOrderByOrderId(customerId, orderId);
		boolean paymentSuccess = paymentManager.doPayment(card);
		if(paymentSuccess) {
			order.setStatus(OrderStatus.COMPLETED.name());
			orderManager.persistOrder(order);
		} else {
			throw new InvalidPaymentDetailsException("ERR1004","Invalid card details");
		}
		return order;
	}

}
