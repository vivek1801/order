package com.tescotills.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tescotills.order.constants.OrderConstants;
import com.tescotills.order.exception.InvalidOrderException;
import com.tescotills.order.exception.OrderNotFoundException;
import com.tescotills.order.exception.handler.GlobalExceptionHandler;
import com.tescotills.order.model.CreditCard;
import com.tescotills.order.model.LineItems;
import com.tescotills.order.model.Order;
import com.tescotills.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/orderservice/" + OrderConstants.API_VERSION + OrderConstants.ORDERS)
@Api(value = "Order Service", description = "Order service is helpful in creation of orders, viewing orders and placing it. It performs CRUD opearations related with orders. This service is interacting with Product service to fetch product details. It also interacts with Payment Service while placing the order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@PostMapping("")
	@ApiOperation(value = "This method performs Creation of a new Order. If should be called when there is no order id associated with a customer")
	public ResponseEntity<Order> addNewOrder(
			@ApiParam(value = "Customer Id, id for which new order is to be created", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@ApiParam(value = "Line Item, it contains the details of the product chosen for order", required = true) @RequestBody final LineItems item)
			throws Exception {
		List<LineItems> items = new ArrayList<>();
		items.add(item);
		Order order = orderService.addItemsToNewOrder(customerId, items);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(order, httpHeaders, HttpStatus.OK);
	}

	@PostMapping("/{orderId}")
	@ApiOperation(value = "This method performs adding items into given order ")
	public ResponseEntity<Order> addItems(@ApiParam(value = "Customer Id associated with the order", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@ApiParam(value = "Order Id for particular order where items are to be added", required = true) @PathVariable(value = OrderConstants.ORDER_ID) String orderId,
			@ApiParam(value = "Line Item, it contains the details of the product chosen for order", required = true) @RequestBody final LineItems item)
			throws Exception {
		 log.info("orderId="+orderId+"customerId="+customerId+"items="+item);
		List<LineItems> items = new ArrayList<>();
		items.add(item);
		Order order = orderService.addItemsToOrder(customerId, orderId, items);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(order, httpHeaders, HttpStatus.CREATED);
	}

	/*
	 * @param - orderId This method is used to fetch the order related with a
	 * customerId
	 */
	@GetMapping("/{orderId}")
	@ApiOperation(value = "This method performs fetching of Order against a given order Id")
	public ResponseEntity<Order> getOrderByOrderId(@ApiParam(value = "Customer Id associated with the order", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@ApiParam(value = "Order Id related with order which needs to be fetched", required = true)  @PathVariable(value = OrderConstants.ORDER_ID) String orderId) throws InvalidOrderException {

		Order order = orderService.getOrderByOrderId(customerId, orderId);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(order, httpHeaders, HttpStatus.OK);
	}

	//@GetMapping("/")
	@ApiOperation(value = "This method performs fetching of all Orders against a given customer")
	public ResponseEntity<List<Order>> listOrders(@ApiParam(value = "Customer Id associated with the order", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId)
			throws InvalidOrderException {

		List<Order> orders = orderService.getOrders(customerId);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(orders, httpHeaders, HttpStatus.OK);
	}

	@PutMapping("/{orderId}/items")
	@ApiOperation(value = "This method updates the existing order against a given customer")
	public ResponseEntity<Order> updateItem(@ApiParam(value = "Customer Id associated with the order", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@ApiParam(value = "Order Id related with order which needs to be updated", required = true) @PathVariable(value = OrderConstants.ORDER_ID) String orderId, @RequestBody final LineItems items)
			throws OrderNotFoundException {
		HttpHeaders httpHeaders = new HttpHeaders();
		Order order = null;
		try {
			order = orderService.updateItemInOrder(customerId, orderId, items);
		} catch (Exception e) {
			return new ResponseEntity<>(order, httpHeaders, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(order, httpHeaders, HttpStatus.OK);
	}
	
	@PostMapping("/{orderId}/update/items")
	@ApiOperation(value = "This method updates the existing order against a given customer")
	public ResponseEntity<Order> updateItemPost(@ApiParam(value = "Customer Id associated with the order", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@ApiParam(value = "Order Id related with order which needs to be updated", required = true) @PathVariable(value = OrderConstants.ORDER_ID) String orderId, @RequestBody final LineItems items)
			throws OrderNotFoundException {
		return updateItem(customerId, orderId, items);
	}

	@DeleteMapping("/{orderId}/items/{productid}")
	@ApiOperation(value = "This method deletes a product from the existing order, against a given customer and order Id")
	public ResponseEntity<Order> deleteItemsFromOrder(
			@ApiParam(value = "Customer Id associated with the order", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@ApiParam(value = "Product Id related with product which needs to be deleted", required = true) @PathVariable(value = OrderConstants.PRODUCT_ID) String productId,
			@ApiParam(value = "Order Id related with order ", required = true) @PathVariable(value = OrderConstants.ORDER_ID) String orderId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			if (!orderService.deleteItemsInOrder(customerId, orderId, productId)) {
				return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
	}

	@GetMapping("/{orderId}/items/delete/{productid}")
	@ApiOperation(value = "This method deletes a product from the existing order, against a given customer and order Id(PUT METHOD implementation of DELETE operation)")
	public ResponseEntity<Order> deleteItemsFromOrderHttpPut(
			@RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@PathVariable(value = OrderConstants.PRODUCT_ID) String productId,
			@PathVariable(value = OrderConstants.ORDER_ID) String orderId) {
		return deleteItemsFromOrder(customerId, productId, orderId);
	}

	//@DeleteMapping("/{id}")
	public void deleteOrders() {
	}

	@PostMapping("/{orderId}/checkout")
	@ApiOperation(value = "This method is used for placing an order for a customer.")
	public ResponseEntity<Order> checkoutOrder(@ApiParam(value = "Customer Id associated with the order", required = true) @RequestParam(value = OrderConstants.CUSTOMER_ID) String customerId,
			@ApiParam(value = "Order Id related with order to be checkout", required = true) @PathVariable(value = OrderConstants.ORDER_ID) String orderId, @RequestBody final CreditCard card)
			throws Exception {
		Order order = orderService.checkout(customerId, orderId, card);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(order, httpHeaders, HttpStatus.CREATED);
	}

}
