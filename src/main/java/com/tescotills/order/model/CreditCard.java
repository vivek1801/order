package com.tescotills.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "All details about the Credit Card. ")
public class CreditCard {
	
	@ApiModelProperty(notes = "Card number of a Credit card")
	private String cardNumber;
	
	@ApiModelProperty(notes = "Expiry date of the Credit Card")
	private String expiry;
	
	@ApiModelProperty(notes = "CVV number of a Credit Card")
	private String cvv;
	
}
