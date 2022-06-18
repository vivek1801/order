package com.tescotills.order.features;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderCreationStepDefs {
	
	@Given("^I do already have an Order created with order Id - \"([^\"]*)\"$")
	public void i_do_already_have_an_Order_created_with_order_Id(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@When("^I am adding items to my order$")
	public void i_am_adding_items_to_my_order() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@Then("^Item should be added into existing order$")
	public void item_should_be_added_into_existing_order() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@Given("^I do not have any Order created$")
	public void i_do_not_have_any_Order_created() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@Then("^New Order should be created$")
	public void new_Order_should_be_created() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

}
