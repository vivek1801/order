package com.tescotills.order.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tescotills.order.constants.OrderStatus;
import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Order;

public class MockOrders {
	
	public static final String ORDER_ID_1 = "5000001";

	public static final String CUST_ID_1 = "1111";
	
	public static final double ORDER_PRICE_INITIAL = 42.99;
	
	public static final double ORDER_PRICE_UPDATED = 85.98;
	
	public static final int MOCK_QUANTITY = 3;
	
	public static final int MOCK_BULKBUY = 5;
	
	private static LineItems getMockItem(Long productId, int mockQuantity, int mockBulkbuy) {
		double price = 42.99;
		return LineItems.builder().productId(productId).quantity(mockQuantity).price(price).bulkbuylimit(mockBulkbuy)
				.build();
	}

	private static long getRandomLongBetween(double min, double max) {
		Double random = (int) (Math.random() * ((max - min) + 1)) + min;
		return random.longValue();
	}
	
	public static List<LineItems> getMultipleItems(String productId, int quantity, int bulkbuy) {
		List<LineItems> items = new ArrayList<>();
		items.add(getMockItem(Long.valueOf(productId), quantity, bulkbuy));
		items.add(getMockItem(getRandomLongBetween(500, 1000), quantity, bulkbuy));
		return items;
	}

	public static Order getMultipleItemsOrderWithProduct(String orderId, String customerId, String productId, int quantity, int bulkbuy) {
		return Order.builder().customerId(customerId).orderId(orderId).status(OrderStatus.CREATED.toString())
				.items(getMultipleItems(productId, quantity, bulkbuy)).totalprice(ORDER_PRICE_INITIAL).build();
	}
	public static Order getMockOrderOne() {
		 return Order.builder().customerId(CUST_ID_1).orderId(ORDER_ID_1).status(OrderStatus.CREATED.toString())
		   .items(Arrays.asList(getMockItemOneInitial())).totalprice(ORDER_PRICE_INITIAL).build();
		}
	
	public static LineItems getMockItemOneInitial() {
		 long productId = 1225928L;
		 int quantity = 1;
		 double price = 42.99;
		 int bulkbuylimit = 5;
		 return LineItems.builder().productId(productId).quantity(quantity).price(price).bulkbuylimit(bulkbuylimit)
		   .build();
		}


}
