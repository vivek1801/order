package com.tescotills.order.features;

import static com.tescotills.order.features.MockOrders.CUST_ID_1;
import static com.tescotills.order.features.MockOrders.ORDER_ID_1;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tescotills.order.controller.OrderController;
import com.tescotills.order.model.Order;
import com.tescotills.order.service.impl.OrderManagerImpl;
import com.tescotills.order.service.impl.OrderServiceImpl;
import com.tescotills.order.service.impl.OrderToolsImpl;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderDeleteStepDefs {
	
	OrderController orderController;

	@Mock
	OrderServiceImpl orderService;

	@InjectMocks
	OrderManagerImpl orderManager;

	@Mock
	OrderToolsImpl orderTools;
	
	Order mockOrder = null;

	@Before
	public void setUp() {
		orderController = new OrderController();
		MockitoAnnotations.initMocks(this);
	}
	
	@Given("^product-(\\d+) should be already present in the order-(\\d+) for customer-(\\d+)$")
	public void product_should_be_already_present_in_the_order_for_customer(String productId, String orderId, String customerId) throws Throwable {
		mockOrder = MockOrders.getMultipleItemsOrderWithProduct(orderId, customerId, productId, MockOrders.MOCK_QUANTITY, MockOrders.MOCK_BULKBUY);
		Boolean isPresent = mockOrder.getItems().stream().filter(itm -> Long.toString(itm.getProductId()).equals(productId)).findFirst().isPresent();
		Assert.assertTrue(isPresent);
	}

	@When("^I remove (\\d+) from order # (\\d+)$")
	public void i_remove_from_order(String productId, String orderId) throws Throwable {
		Mockito.when(orderTools.findOrder(orderId)).thenReturn(mockOrder);
		Mockito.doNothing().when(orderTools).persistOrder(Mockito.any(Order.class));
		boolean b = orderManager.deleteItems(CUST_ID_1, ORDER_ID_1, productId);
		assertEquals(true, b);
	}

	@Then("^(\\d+) is not present in order$")
	public void is_not_present_in_order(String productId) throws Throwable {
		Boolean isPresent = mockOrder.getItems().stream().filter(itm -> Long.toString(itm.getProductId()).equals(productId)).findFirst().isPresent();
		Assert.assertFalse(isPresent);
		Assert.assertEquals(1, mockOrder.getItems().size());
	}
	
	@Given("^order-(\\d+) is created for customerid-(\\d+) having product-(\\d+)$")
	public void order_is_created_for_customerid_having_product(String orderId, String customerId, String productId) throws Throwable {
		mockOrder = MockOrders.getMultipleItemsOrderWithProduct(orderId, customerId, productId, MockOrders.MOCK_QUANTITY, MockOrders.MOCK_BULKBUY);
	}

	@When("^I remove productid-(\\d+) from order-(\\d+)$")
	public void i_remove_productid_from_order(String productId, String orderId) throws Throwable {
		Mockito.when(orderTools.findOrder(orderId)).thenReturn(mockOrder);
		Mockito.doNothing().when(orderTools).persistOrder(Mockito.any(Order.class));
		boolean b = orderManager.deleteItems(CUST_ID_1, ORDER_ID_1, productId);
		assertEquals(true, b);
	}

	@Then("^(\\d+) is same as that present in order-(\\d+)$")
	public void is_same_as_that_present_in_order(String customerId, String orderId) throws Throwable {
		Assert.assertEquals(customerId, mockOrder.getCustomerId());
		Assert.assertEquals(orderId, mockOrder.getOrderId());
	}


}