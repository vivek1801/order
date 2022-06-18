package com.tescotills.order.features;

import java.util.Optional;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Order;
import com.tescotills.order.service.impl.OrderManagerImpl;
import com.tescotills.order.service.impl.OrderServiceImpl;
import com.tescotills.order.service.impl.OrderToolsImpl;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderUpdateStepDefs {

	@Mock
	OrderServiceImpl orderService;

	@InjectMocks
	OrderManagerImpl orderManager;

	@Mock
	OrderToolsImpl orderTools;

	Order mockOrder = null;

	Exception exceptionHolder = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Given("^I have product:(\\d+) and quantity:(\\d+) and bulkbuylimit:(\\d+) for orderId:(\\d+) with customerId:(\\d+)$")
	public void i_have_product_and_quantity_and_bulkbuylimit_for_orderId_with_customerId(String productId, int quantity,
			int bulkbuylimit, String orderId, String customerId) throws Throwable {
		mockOrder = MockOrders.getMultipleItemsOrderWithProduct(orderId, customerId, productId, quantity, bulkbuylimit);
	}

	@When("^I add same product:(\\d+) and  quantity as quantity:(\\d+) to my orderId:(\\d+) with customerId:(\\d+) having bulkbuylimit:(\\d+)$")
	public void i_add_same_product_and_quantity_as_quantity_to_my_orderId_with_customerId_having_bulkbuylimit(
			long productId, int quantity, String orderId, String customerId, int bulkbuylimit) throws Throwable {
		Mockito.when(orderTools.findOrder(orderId)).thenReturn(mockOrder);
		Mockito.doNothing().when(orderTools).update(Mockito.any(Order.class));
		LineItems item = LineItems.builder().productId(productId).quantity(quantity).bulkbuylimit(bulkbuylimit)
				.price(55.85).build();
		try {
			mockOrder = orderManager.updateItem(customerId, orderId, item);
		} catch (Exception e) {
			exceptionHolder = e;
		}
	}

	@Then("^the transaction completes with status:success and quantity:(\\d+) for product:(\\d+) is present in orderId:(\\d+) with customerId:(\\d+)$")
	public void the_transaction_completes_with_status_success_and_quantity_for_product_is_present_in_orderId_with_customerId(
			int quantity, String productId, String orderId, String customerId) throws Throwable {
		Optional<LineItems> itemHolder = mockOrder.getItems().stream()
				.filter(itm -> Long.toString(itm.getProductId()).equals(productId)).findFirst();
		LineItems item = itemHolder.get();
		Assert.assertEquals(quantity, item.getQuantity());
		Assert.assertEquals(customerId, mockOrder.getCustomerId());
	}

	@Then("^the transaction completes with status:failure and quantity:(\\d+) for product:(\\d+) is present in orderId:(\\d+) with customerId:(\\d+)$")
	public void the_transaction_completes_with_status_failure_and_quantity_for_product_is_present_in_orderId_with_customerId(
			int quantity, String productId, String orderId, String customerId) throws Throwable {
		Assert.assertEquals("Bulkbuy restrictions violation", exceptionHolder.getMessage());
	}

}
