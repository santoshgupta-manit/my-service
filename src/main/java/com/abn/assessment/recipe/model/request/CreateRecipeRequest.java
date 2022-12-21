package com.abn.assessment.recipe.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.abn.assessment.recipe.validator.OneOf;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateRecipeRequest {
	@NotBlank(message = "{api.recipe.create.name.notBlank}")
	@ApiModelProperty(example = "sandwich", position = 1)
	private String name;
	@NotBlank(message = "{api.recipe.create.type.notBlank}")
	@OneOf(message = "{api.recipe.create.type.invalid}", value = { "vegetarian", "non-vegetarian" })
	@ApiModelProperty(example = "vegetarian", position = 2, allowableValues = "vegetarian,non-vegetarian")
	private String type;
	@NotNull(message = "{api.recipe.create.numberOfServings.notNull}")
	@Positive(message = "{api.recipe.create.numberOfServings.positive}")
	@ApiModelProperty(example = "1", position = 3)
	private int numberOfServings;
	@NotBlank(message = "{api.recipe.create.instructions.notBlank}")
	@ApiModelProperty(example = "Step 1: Spread the jam onto one slice of bread. Step 2: Spread the butter onto one slice of bread. Step 3: Get two slices of bread.", position = 4)
	private String instructions;
	@NotEmpty(message = "{api.recipe.create.ingredientList.notEmpty}")
	@Valid
	private List<IngredientReq> ingredients;

}
