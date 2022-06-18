Feature: Creation of Order 

Scenario:
If an order exists already, item should be added into Existing Order only 
	Given I do already have an Order created with order Id - "Order12345" 
	When I am adding items to my order 
	Then Item should be added into existing order 
	
Scenario: If there is no existing order, a new order should be created 
	Given I do not have any Order created 
	When I am adding items to my order 
	Then New Order should be created 
	
	
    