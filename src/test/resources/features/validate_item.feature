Feature: The Item added should be a valid item 

Scenario: Valid Item should be added to the order 

	Given  An order containg 1 item 
	When   I add 1 item with 10 digit product id 
	Then   I should have 2 item with 10 digit product id 
	
Scenario: InValid Item should not  be added to the order 

	Given  An order containg 1 item 
	When   I add 1 item with 11 digit product id 
	Then   I should have only 1 item 