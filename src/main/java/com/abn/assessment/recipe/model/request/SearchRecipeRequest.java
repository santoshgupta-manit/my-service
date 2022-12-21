package com.abn.assessment.recipe.model.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchRecipeRequest {
	@NotEmpty(message = "{api.recipe.filterSearch.searchCriteriaRequest.notEmpty}")
	@Valid
	private List<SearchCriteria> searchCriteria;
}
