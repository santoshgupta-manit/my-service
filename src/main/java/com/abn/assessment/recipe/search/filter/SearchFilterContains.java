package com.abn.assessment.recipe.search.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.abn.assessment.recipe.repository.entity.Recipe;
import com.abn.assessment.recipe.search.SearchOperation;

public class SearchFilterContains implements SearchFilter {

	@Override
	public boolean couldBeApplied(SearchOperation opt) {
		return opt == SearchOperation.CONTAINS;
	}

	@Override
	public Predicate apply(CriteriaBuilder cb, String filterKey, String filterValue, Root<Recipe> root) {
		if (filterKey.equalsIgnoreCase("ingredients")) {
			return cb.like(cb.lower(root.get(filterKey).as(String.class)), "%\"" + filterValue + "\",%");
		} else {
			return cb.like(cb.lower(root.get(filterKey).as(String.class)), "%" + filterValue + "%");
		}
	}
}
