
package com.abn.assessment.recipe.search;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.abn.assessment.recipe.model.request.SearchCriteria;
import com.abn.assessment.recipe.repository.entity.Recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class RecipeSpecificationBuilder {
	private final List<SearchCriteria> params;

	public final RecipeSpecificationBuilder with(SearchCriteria searchCriteria) {
		params.add(searchCriteria);
		return this;
	}

	public Optional<Specification<Recipe>> build() {
		if (params.isEmpty())
			return Optional.empty();

		Specification<Recipe> result = new RecipeSpecification(params.get(0));

		for (int i = 1; i < params.size(); i++) {
			SearchCriteria criteria = params.get(i);
			result = Specification.where(result).and(new RecipeSpecification(criteria));
		}
		return Optional.of(result);
	}
}
