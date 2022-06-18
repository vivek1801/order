package com.tescotills.order.service;

import java.util.List;

import com.tescotills.order.exception.ProductNotFoundException;
import com.tescotills.order.model.LineItems;

public interface ProductManager {

	List<LineItems> getItemPrices(List<LineItems> items) throws ProductNotFoundException;


}
