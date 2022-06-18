package com.tescotills.order.features;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderValidateItemStepDefs {
	@Given("^An order containg (\\d+) item$")
	public void an_order_containg_item(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@When("^I add (\\d+) item with (\\d+) digit product id$")
	public void i_add_item_with_digit_product_id(int arg1, int arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@Then("^I should have (\\d+) item with (\\d+) digit product id$")
	public void i_should_have_item_with_digit_product_id(int arg1, int arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@Then("^I should have only (\\d+) item$")
	public void i_should_have_only_item(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

}
