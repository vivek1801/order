package com.tescotills.order.model;

import java.util.List;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document
@ApiModel(description = "This class contains the details of the Order placed by customer. ")
public class Order {
	
	@Id
	@ApiModelProperty(notes = "Order Id realted with the a customer")
	private String orderId;
	
	@Field
	@ApiModelProperty(notes = "Customer Id of customer who is placing the order")
	private String customerId;
	
	@Field
	@ApiModelProperty(notes = "It contains the list of <code>LineItems</code> added in the order")
	private List<LineItems> items;
	
	@Field
	@ApiModelProperty(notes = "The total price, summation of price of all the line items")
	private double totalprice;
	
	@Field
	@ApiModelProperty(notes = "It represents the current status of order. Possible values are : PENDING, SUCCESS, FAILED")
	private String status;
	
	public Order() {
	}
	
	public Order(String orderId, String customerId, List<LineItems> items, double totalprice, String status) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.items = items;
		this.totalprice = totalprice;
		this.status = status;
	}
	
}
