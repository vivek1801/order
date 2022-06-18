Feature: Add item in the Order 

Scenario: Adding an item to an Order should not break Bulk restriction 
	Given I have 2 apples in my order 
	When I am adding 3 more apples to my order 
	Then I should get an error saying "maximum limit reached" 
	
Scenario Outline: Adding an item to an Order should not break Bulk restriction 
	Given  I have "<quantity>" "<item-name>" in my order 
	When I am adding "<quantity>" more "<item-name>" to my order 
	Then  I should get an error saying "maximum limit reached" 
	
	Examples: 
		|  item-name | 	quantity |
		|  	apples 	 |  	  1		|
		|  	apples	 |    	2		|
    