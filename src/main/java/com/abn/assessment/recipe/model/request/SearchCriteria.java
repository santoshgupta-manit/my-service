package com.abn.assessment.recipe.model.request;

import javax.validation.constraints.NotNull;

import com.abn.assessment.recipe.validator.OneOf;

import io.swagger.annotations.ApiModelProperty;
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
public class SearchCriteria {
	@NotNull(message = "{api.recipe.filterSearch.filterKey.notBlank}")
	@OneOf(message = "{api.recipe.filterSearch.filterKey.invalid}", value = { "name", "numberOfServings", "type",
			"instructions", "ingredients" })
	@ApiModelProperty(example = "name", required = true)
	private String filterKey;
	@NotNull(message = "{api.recipe.filterSearch.value.notBlank}")
	@ApiModelProperty(example = "sandwich", required = true)
	private Object value;
	@NotNull(message = "{api.recipe.filterSearch.operation.oneOf}")
	@OneOf(message = "{api.recipe.filterSearch.searchOperation.invalid}", value = { "cn", "nc", "eq", "ne" })
	@ApiModelProperty(example = "eq", required = true)
	private String operation;

}
