package com.abn.assessment.recipe.model.response;

import org.springframework.http.HttpStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceMessage {
	@ApiModelProperty(example = "SUCCESS")
	private String message;
	@ApiModelProperty(example = "200")
	private int statusCode;
	@ApiModelProperty(example = "Request completed successfully.")
	private String description;

}
