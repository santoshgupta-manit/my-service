package com.abn.assessment.recipe.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.abn.assessment.recipe.model.request.SearchCriteria;
import com.abn.assessment.recipe.repository.entity.Recipe;
import com.abn.assessment.recipe.search.filter.SearchFilter;
import com.abn.assessment.recipe.search.filter.SearchFilterContains;
import com.abn.assessment.recipe.search.filter.SearchFilterDoesNotContain;
import com.abn.assessment.recipe.search.filter.SearchFilterEqual;
import com.abn.assessment.recipe.search.filter.SearchFilterNotEqual;

public class RecipeSpecification implements Specification<Recipe> {
	private static final long serialVersionUID = 1L;

	private final SearchCriteria criteria;

	private static final List<SearchFilter> searchFilters = new ArrayList<>();

	public RecipeSpecification(SearchCriteria criteria) {
		super();
		filterList();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Optional<SearchOperation> operation = SearchOperation.getOperation(criteria.getOperation());
		String filterValue = criteria.getValue().toString().toLowerCase();
		String filterKey = criteria.getFilterKey();
		query.distinct(true);

		return operation.flatMap(searchOperation -> searchFilters.stream()
				.filter(searchFilter -> searchFilter.couldBeApplied(searchOperation)).findFirst()
				.map(searchFilter -> searchFilter.apply(cb, filterKey, filterValue, root))).orElse(null);
	}

	private void filterList() {
		searchFilters.add(new SearchFilterEqual());
		searchFilters.add(new SearchFilterNotEqual());
		searchFilters.add(new SearchFilterContains());
		searchFilters.add(new SearchFilterDoesNotContain());
	}



}
