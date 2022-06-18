Feature: As a user,  I want to view the latest order along with items, qty , line item price and total order value

@abcd    
Scenario Outline: Display  the line item along with price  the latest price and qty ( latest unit price * qty ).
Given I have "<orderId>" and "<customerId>"
When  I make a request to view the order along with lineitems ,orderPrice
Then I should  able to see my order along with lineitems,total orderprice

Examples:
|orderId|customerId|
|5000001|1111|
    