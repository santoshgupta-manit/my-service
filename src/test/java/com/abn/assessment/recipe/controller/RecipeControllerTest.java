package com.abn.assessment.recipe.controller;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abn.assessment.recipe.enums.StatusEnum;
import com.abn.assessment.recipe.model.request.CreateRecipeRequest;
import com.abn.assessment.recipe.model.request.IngredientReq;
import com.abn.assessment.recipe.model.request.SearchCriteria;
import com.abn.assessment.recipe.model.request.SearchRecipeRequest;
import com.abn.assessment.recipe.model.request.UpdateRecipeRequest;
import com.abn.assessment.recipe.model.response.CreateRecipeResponse;
import com.abn.assessment.recipe.model.response.IngredientRes;
import com.abn.assessment.recipe.model.response.RecipeResponse;
import com.abn.assessment.recipe.model.response.SearchRecipeResponse;
import com.abn.assessment.recipe.model.response.ServiceMessage;
import com.abn.assessment.recipe.model.response.ServiceResponse;
import com.abn.assessment.recipe.service.RecipeService;
import com.abn.assessment.recipe.util.RecipeUtil;

import test.object.builder.TestDataBuilder;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
	
	@InjectMocks
    private RecipeController recipeController;
	
	@Mock
    private RecipeService recipeService;

    
    @Test
    void test_createRecipe() {
    	when(recipeService.createRecipe(Mockito.any(CreateRecipeRequest.class))).thenReturn(TestDataBuilder.buildCreateRecipeResponse());
    	ResponseEntity<CreateRecipeResponse> response = recipeController.createRecipe(TestDataBuilder.buildCreateRecipeRequest());
        assertThat(response).isNotNull();
        assertThat(response.getBody().getId()).isSameAs(1);
    }
    
    @Test
    void test_updateRecipe() {
    	doNothing().when(recipeService).updateRecipe(Mockito.any());
    	ResponseEntity<ServiceResponse> response = recipeController.updateRecipe(TestDataBuilder.buildUpdateRecipeRequest());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    void test_deleteRecipe() {
    	doNothing().when(recipeService).deleteRecipe(Mockito.anyInt());
    	ResponseEntity<ServiceResponse> response = recipeController.deleteRecipe(1);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    void test_getRecipe() {
    	when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(TestDataBuilder.buildRecipeResponse());
    	ResponseEntity<RecipeResponse> response = recipeController.getRecipe(1);
        assertThat(response).isNotNull();
        assertThat(response.getBody().getId()).isSameAs(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    void test_getRecipeList() {
    	when(recipeService.getRecipeList(Mockito.anyInt(),Mockito.anyInt())).thenReturn(TestDataBuilder.buildSearchRecipeResponse());
    	ResponseEntity<SearchRecipeResponse> response = recipeController.getRecipeList(0,10);
        assertThat(response).isNotNull();
        assertThat(response.getBody().getRecipeList().size()).isSameAs(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    void test_searchRecipe() {
    	when(recipeService.findRecipes(Mockito.any(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(TestDataBuilder.buildSearchRecipeResponse());
    	ResponseEntity<SearchRecipeResponse> response = recipeController.searchRecipes(0,10,TestDataBuilder.buildSearchRecipeRequest());
        assertThat(response).isNotNull();
        assertThat(response.getBody().getRecipeList().size()).isSameAs(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
