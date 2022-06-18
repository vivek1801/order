package com.tescotills.order.features;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderAddStepDefs {
	
	@Given("^I have (\\d+) apples in my order$")
	public void i_have_apples_in_my_order(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@When("^I am adding (\\d+) more apples to my order$")
	public void i_am_adding_more_apples_to_my_order(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

	@Then("^I should get an error saying \"([^\"]*)\"$")
	public void i_should_get_an_error_saying(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
	}

}
