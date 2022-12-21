package com.abn.assessment.recipe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.abn.assessment.recipe.model.request.CreateRecipeRequest;
import com.abn.assessment.recipe.model.request.SearchCriteria;
import com.abn.assessment.recipe.model.request.SearchRecipeRequest;
import com.abn.assessment.recipe.model.response.CreateRecipeResponse;
import com.abn.assessment.recipe.model.response.RecipeResponse;
import com.abn.assessment.recipe.model.response.SearchRecipeResponse;
import com.abn.assessment.recipe.repository.RecipeRepository;
import com.abn.assessment.recipe.repository.entity.Recipe;

import test.object.builder.TestDataBuilder;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

	@InjectMocks
	private RecipeService recipeService;
	@Mock
	private RecipeRepository recipeRepository;
	@Mock
	private MessageProvider messageProvider;

	CreateRecipeRequest createRecipeRequest;
	CreateRecipeResponse createRecipeResponse;

	@BeforeEach
	public void setUp() {

	}

	@Test
	void test_createRecipe() {
		when(recipeRepository.findByNameIgnoreCase(Mockito.anyString())).thenReturn(null);
		when(recipeRepository.save(Mockito.any())).thenReturn(TestDataBuilder.buildRecipe());
		CreateRecipeResponse response = recipeService.createRecipe(TestDataBuilder.buildCreateRecipeRequest());
		assertThat(response).isNotNull();
	}

	@Test
	void test_updateRecipe() {
		when(recipeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(TestDataBuilder.buildRecipe()));
		when(recipeRepository.save(Mockito.any())).thenReturn(TestDataBuilder.buildRecipe());
		recipeService.updateRecipe(TestDataBuilder.buildUpdateRecipeRequest());
		verify(recipeRepository, times(1)).save(Mockito.any());
	}

	@Test
	void test_deleteRecipe() {
		when(recipeRepository.existsById(Mockito.anyInt())).thenReturn(true);
		doNothing().when(recipeRepository).deleteById(Mockito.anyInt());
		recipeService.deleteRecipe(1);
		verify(recipeRepository, times(1)).deleteById(Mockito.any());
	}

	@Test
	void test_getRecipe() {
		when(recipeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(TestDataBuilder.buildRecipe()));
		RecipeResponse response = recipeService.getRecipeById(1);
		assertThat(response).isNotNull();
	}

	@Test
	void test_getRecipeList() {
		List<Recipe> recipeList = new ArrayList<>();
		recipeList.add(TestDataBuilder.buildRecipe());
		Page<Recipe> pagedResponse = new PageImpl<Recipe>(recipeList);
		when(recipeRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(pagedResponse);
		SearchRecipeResponse response = recipeService.getRecipeList(0, 10);
		assertThat(response).isNotNull();
	}

	@Test
	void test_findRecipes() {
		List<Recipe> recipeList = new ArrayList<>();
		recipeList.add(TestDataBuilder.buildRecipe());
		Page<Recipe> pagedResponse = new PageImpl<Recipe>(recipeList);
		when(recipeRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class)))
				.thenReturn(pagedResponse);
		SearchRecipeRequest searchRecipeRequest = TestDataBuilder.buildSearchRecipeRequest();
		searchRecipeRequest.getSearchCriteria()
				.add(new SearchCriteria("name", "tea", "ne"));
		SearchRecipeResponse response = recipeService.findRecipes(searchRecipeRequest, 0, 10);
		assertThat(response).isNotNull();
	}
}
