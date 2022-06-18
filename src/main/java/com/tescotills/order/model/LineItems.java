package com.tescotills.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@ApiModel(description = "This class contains the details of the line items Of the Order. ")
public class LineItems {
	
	@ApiModelProperty(notes = "Product Id of a product which is in the line items")
	private long productId;
	
	@ApiModelProperty(notes = "Quantity of a product for this Order")
	private int quantity;
	
	@ApiModelProperty(notes = "Price, the amount equal to unit price multiplied by quantity")
	private double price;
	
	@ApiModelProperty(notes = "The maximum allowed quantities of a product per customer")
	private int bulkbuylimit;
	
}
