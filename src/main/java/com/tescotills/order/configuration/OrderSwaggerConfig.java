package com.tescotills.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.tescotills.order.constants.OrderConstants;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class OrderSwaggerConfig {
	@Bean
	public Docket restApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.tescotills.order"))
				.paths(regex("/orderservice/" + OrderConstants.API_VERSION + OrderConstants.ORDERS + "/.*")).build()
				.apiInfo(metaData());

	}

	private ApiInfo metaData() {

		ApiInfo apiInfo = new ApiInfo("Our Tesco Training API", "Some custom description of API.", "API TESCO",
				"Terms of service",
				new Contact("Publicis Sapient", "www.publicissapient.com", "www.publicissapient.com"),
				"License of API", "API license URL", Collections.emptyList());
		return apiInfo;

	}
}
