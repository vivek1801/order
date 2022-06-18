package com.tescotills.order.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tescotills.order.adapter.ProductClient;
import com.tescotills.order.exception.ProductNotFoundException;
import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Product;
import com.tescotills.order.service.ProductManager;

@Service
public class ProductManagerImpl implements ProductManager {

	@Autowired
	ProductClient productClient;

	@Override
	public List<LineItems> getItemPrices(List<LineItems> items) throws ProductNotFoundException {
		List<LineItems> pricedItems = items.parallelStream().map(i -> {
			Product product = productClient.getProduct(i.getProductId());
			double unitPrice = product.getPrice();
			double lineItemPrice = unitPrice * i.getQuantity();
			i.setPrice(lineItemPrice);
			i.setBulkbuylimit(product.getBulkbuylimit());
			return i;
		}).collect(Collectors.toList());
		return pricedItems;
	}
}
