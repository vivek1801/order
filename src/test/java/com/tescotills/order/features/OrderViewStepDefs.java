package com.tescotills.order.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.tescotills.order.model.Order;
import com.tescotills.order.repository.OrderRepository;
import com.tescotills.order.service.impl.OrderManagerImpl;
import com.tescotills.order.service.impl.OrderServiceImpl;
import com.tescotills.order.service.impl.OrderToolsImpl;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class OrderViewStepDefs {
	
	private String customerId;

	private String orderId;
	
	@Mock
	OrderServiceImpl orderService;

	@InjectMocks
	OrderManagerImpl orderManager;

	@Mock
	OrderToolsImpl orderTools;
	
	@Mock
	OrderRepository orderRepository;
	
	Order mockOrder = null;
	Order actualOrder = null;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Given("^I have \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_have_and(String orderId, String customerId) throws Throwable {
		this.customerId = customerId;
		this.orderId = orderId;
	}

	@When("^I make a request to view the order along with lineitems ,orderPrice$")
	public void i_make_a_request_to_view_the_order_along_with_lineitems_orderPrice() throws Throwable {
		mockOrder = MockOrders.getMockOrderOne();
		Mockito.when(orderTools.findOrder(orderId)).thenReturn(mockOrder);
		actualOrder = orderManager.getOrder(orderId,customerId) ;    
	}

	@Then("^I should  able to see my order along with lineitems,total orderprice$")
	public void i_should_able_to_see_my_order_along_with_lineitems_total_orderprice() throws Throwable {
	    assertNotNull(actualOrder);
	    assertNotNull(actualOrder.getItems());
	    assertEquals(true, actualOrder.getItems().size()>0);
	}

}
