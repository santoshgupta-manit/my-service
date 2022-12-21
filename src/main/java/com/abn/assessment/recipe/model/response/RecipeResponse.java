package com.abn.assessment.recipe.model.response;

import java.time.LocalDateTime;
import java.util.List;

import com.abn.assessment.recipe.repository.entity.Recipe;
import com.abn.assessment.recipe.util.RecipeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecipeResponse extends ServiceResponse {
	@ApiModelProperty(example = "1")
	private int id;
	@ApiModelProperty(example = "sandwich")
	private String name;
	@ApiModelProperty(example = "vegetarian")
	private String type;
	@ApiModelProperty(example = "1")
	private int numberOfServings;
	
	@ApiModelProperty(example = "Step 1: Spread the jam onto one slice of bread. Step 2: Spread the butter onto one slice of bread. Step 3: Get two slices of bread.", position = 4)
	private String instructions;
	
	private List<IngredientRes> ingredients;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(example = "2022-12-19 20:10:00")
	private LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(example = "2022-12-19 20:120:00")
	private LocalDateTime lastUpdatedAt;

	public RecipeResponse(Recipe recipe) {
		this.id = recipe.getId();
		this.name = recipe.getName();
		this.type = recipe.getType();
		this.instructions = recipe.getInstructions();
		this.createdAt = recipe.getCreatedAt();
		this.lastUpdatedAt = recipe.getLastUpdatedAt();
		this.numberOfServings = recipe.getNumberOfServings();
		this.ingredients = RecipeUtil.convertToIngredientsList(recipe.getIngredients());
	}

}
