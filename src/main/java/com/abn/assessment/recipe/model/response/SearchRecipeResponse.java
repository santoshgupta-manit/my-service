package com.abn.assessment.recipe.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRecipeResponse extends ServiceResponse {
	List<RecipeResponse> recipeList;
}
