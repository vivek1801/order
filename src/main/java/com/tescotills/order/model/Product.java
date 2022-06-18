package com.tescotills.order.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Document
@ApiModel(description = "This class contains the details of the Product")
public class Product {

	@Id
	@Field
	@ApiModelProperty(notes = "Proudct Id, unique id for each of the product available in inventory")
	private String productid;

	@Field
	@NotNull
	@ApiModelProperty(notes = "It represents unit of measure of each quantity. Possible values of this field are : EA, weight measure units")
	private String uom;

	@Field
	@NotNull
	@ApiModelProperty(notes = "It describes the details of the product")
	private String description;

	@Field
	@NotNull
	@ApiModelProperty(notes = "Unit price of each product")
	private double price;

	@Field
	@NotNull
	@ApiModelProperty(notes = "The maximum allowed quantities of a product per customer")
	private int bulkbuylimit;

	public Product(String productid, String uom, String description, double price, int bulkbuylimit) {
		super();
		this.productid = productid;
		this.uom = uom;
		this.description = description;
		this.price = price;
		this.bulkbuylimit = bulkbuylimit;
	}
}


