package com.abn.assessment.recipe.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IngredientReq {
	@NotNull(message = "{api.recipe.create.ingredient.name.notBlank}")
	@ApiModelProperty(example = "bread")
	String name;
	@ApiModelProperty(example = "2")
	String quantity;
}
