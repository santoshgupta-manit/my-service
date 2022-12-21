package com.abn.assessment.recipe.search.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.abn.assessment.recipe.repository.entity.Recipe;
import com.abn.assessment.recipe.search.SearchOperation;

public interface SearchFilter  {
    boolean couldBeApplied(SearchOperation opt);
    Predicate apply(CriteriaBuilder cb, String filterKey, String filterValue, Root<Recipe> root);
}