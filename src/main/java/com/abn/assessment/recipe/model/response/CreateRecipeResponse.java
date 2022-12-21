package com.abn.assessment.recipe.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateRecipeResponse extends ServiceResponse {
	@ApiModelProperty(example = "1")
	int id;
	@ApiModelProperty(example = "sandwich")
	String name;

}
