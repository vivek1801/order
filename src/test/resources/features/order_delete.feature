@deleteItems
Feature: Order delete feature

#Problem Statement: As a user, I want remove an item from existing order 
#so that I have an accurate list of items in cart

Scenario Outline: ​When I remove an item from existing order, I have an accurate list of items in cart
    Given product-<productId> should be already present in the order-<orderId> for customer-<customerId>
    When I remove <productId> from order # <orderId>
    Then <productId> is not present in order
    Examples: 
     | orderId  |customerId| productId |
     | 5000001  |      1111| 12002528	 |

Scenario Outline: Only owner can remove an item from existing order
    Given order-<orderId> is created for customerid-<customerId> having product-<productId> 
    When I remove productid-<productId> from order-<orderId>
    Then <customerId> is same as that present in order-<orderId>
    Examples: 
      | orderId  |customerId| productId | status|
      | 5000001  |      1111| 5000001		|  true |
      | 5000001	 |      1111| 5000001		| false |
