@updateItems
Feature: Update Order

  #Problem Statement: As a user, I want to be able to update the quantity of existing 
  #items in my order so that I have the right quantity of items to be purchased later
  
  Scenario Outline: update the quantity of existing product in my order with bulk buy Restriction
    Given I have product:<productId> and quantity:<productQuantity> and bulkbuylimit:<bulkBuyRestriction> for orderId:<orderId> with customerId:<customerId>
    When I add same product:<productId> and  quantity as quantity:<productQuantity> to my orderId:<orderId> with customerId:<customerId> having bulkbuylimit:<bulkBuyRestriction>
    Then the transaction completes with status:<status> and quantity:<productQuantity> for product:<productId> is present in orderId:<orderId> with customerId:<customerId>

    Examples: 
      | orderId | productId | productQuantity | bulkBuyRestriction | status  | customerId |
      | 5000001 |  12002528 |               4 |                  5 | success |       1111 |
      | 5000001 |  12002528 |               7 |                  5 | failure |       1111 |
