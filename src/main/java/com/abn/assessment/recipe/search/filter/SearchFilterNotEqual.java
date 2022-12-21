package com.abn.assessment.recipe.search.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.abn.assessment.recipe.repository.entity.Recipe;
import com.abn.assessment.recipe.search.SearchOperation;

public class SearchFilterNotEqual implements SearchFilter {

	@Override
	public boolean couldBeApplied(SearchOperation opt) {
		return opt == SearchOperation.NOT_EQUAL;
	}

	@Override
	public Predicate apply(CriteriaBuilder cb, String filterKey, String filterValue, Root<Recipe> root) {
		if (filterKey.equalsIgnoreCase("ingredients")) {
			return cb.notLike(cb.lower(root.get(filterKey).as(String.class)), "%\"" + filterValue + "\",%");
		} else {
			return cb.notEqual(root.get(filterKey).as(String.class), filterValue);
		}
	}
}
