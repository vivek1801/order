Feature: Customer of the Order should only be able to add items into order 

Scenario: Customer should exist in our System. 
	Given CustomerId should be there with Customer
	When CustomerId does not exist in our system
	Then I should get an error saying "customer does not exist"
	
Scenario: Customer should exist in our System. 
	Given CustomerId should be there with Customer
	When CustomerId does exist in our system
	Then Customer should able to add item in order.  
	

    