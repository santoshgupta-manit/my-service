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
public class IngredientRes {
	@ApiModelProperty(example = "bread")
	String name;
	@ApiModelProperty(example = "2")
	String quantity;
}
