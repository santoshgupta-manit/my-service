package com.abn.assessment.recipe.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusEnum {

	STATUS_200("Request Completed Successfully", HttpStatus.OK),
	STATUS_201("Resource Created Successfully", HttpStatus.CREATED), 
	STATUS_400("Bad Request", HttpStatus.BAD_REQUEST),
	STATUS_404("Resource Not Found", HttpStatus.NOT_FOUND), 
	STATUS_401("Unauthorized", HttpStatus.UNAUTHORIZED),
	STATUS_403("Forbidden", HttpStatus.FORBIDDEN),
	STATUS_405("Request method not supported", HttpStatus.METHOD_NOT_ALLOWED),
	STATUS_406("Invalid request header: Content-Type. Allowed media types: application/json",
			HttpStatus.NOT_ACCEPTABLE),
	STATUS_415("Invalid request header: Content-Type. Allowed media types: application/json",
			HttpStatus.UNSUPPORTED_MEDIA_TYPE),
	STATUS_500("Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
	
	private String description;
	private HttpStatus httpStatus;

}
