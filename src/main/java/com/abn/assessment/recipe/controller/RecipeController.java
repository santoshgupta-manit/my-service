package com.abn.assessment.recipe.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abn.assessment.recipe.enums.StatusEnum;
import com.abn.assessment.recipe.exception.ResourceNotFound;
import com.abn.assessment.recipe.model.request.CreateRecipeRequest;
import com.abn.assessment.recipe.model.request.SearchRecipeRequest;
import com.abn.assessment.recipe.model.request.UpdateRecipeRequest;
import com.abn.assessment.recipe.model.response.CreateRecipeResponse;
import com.abn.assessment.recipe.model.response.RecipeResponse;
import com.abn.assessment.recipe.model.response.SearchRecipeResponse;
import com.abn.assessment.recipe.model.response.ServiceResponse;
import com.abn.assessment.recipe.service.MessageProvider;
import com.abn.assessment.recipe.service.RecipeService;
import com.abn.assessment.recipe.util.RecipeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "v1/recipe")
@Api(value = "RecipeController", tags = "Recipe Controller", description = "Create, update, delete, list and search recipes")
@Slf4j
public class RecipeController {

	private final RecipeService recipeService;
	private final MessageProvider messageProvider;

	public RecipeController(RecipeService recipeService, MessageProvider messageProvider) {
		this.recipeService = recipeService;
		this.messageProvider = messageProvider;
	}

	@ApiOperation(value = "Get recipe by its ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Request completed successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Recipe not found") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<RecipeResponse> getRecipe(
			@PathVariable(name = "id") int id) {
		log.info("Getting the recipe by its id. Id: {}", id);
		RecipeResponse recipeRes = recipeService.getRecipeById(id);
		recipeRes.setMessage(RecipeUtil.buildServiceMessage(StatusEnum.STATUS_200));
		return new ResponseEntity<>(recipeRes, StatusEnum.STATUS_200.getHttpStatus());
	}

	@ApiOperation(value = "List all the recipes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Request completed successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "No recipe not found") })
	@GetMapping(path = "/page/{page}/size/{size}")
	public ResponseEntity<SearchRecipeResponse> getRecipeList( @PathVariable(name = "page") int page,
			@PathVariable(name = "size") int size) {
		log.info("Getting the recipes");
		SearchRecipeResponse response = recipeService.getRecipeList(page, size);
		if (org.apache.commons.collections4.CollectionUtils.isEmpty(response.getRecipeList())) {
			throw new ResourceNotFound(messageProvider.getMessage("no.result.returned"));
		}
		response.setMessage(RecipeUtil.buildServiceMessage(StatusEnum.STATUS_200));
		return new ResponseEntity<>(response, StatusEnum.STATUS_200.getHttpStatus());

	}
	
	@ApiOperation(value = "Create a recipe")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Request completed successfully"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping
	public ResponseEntity<CreateRecipeResponse> createRecipe(
			 @ApiParam(value = "Request object for recipe making", required = true)
			@Valid @RequestBody CreateRecipeRequest request) {
		log.info("Creating the recipe with properties");
		CreateRecipeResponse createRecipeRes = recipeService.createRecipe(request);
		createRecipeRes.setMessage(RecipeUtil.buildServiceMessage(StatusEnum.STATUS_201));
		return new ResponseEntity<>(createRecipeRes, StatusEnum.STATUS_201.getHttpStatus());

	}

	@ApiOperation(value = "Update the recipe")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Request completed successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "No recipe not found")})
	@PatchMapping
	public ResponseEntity<ServiceResponse> updateRecipe(
			@ApiParam(value = "Request object for updating the recipe", required = true)
			@Valid @RequestBody UpdateRecipeRequest request) {
		log.info("Updating the recipe by given properties");
		recipeService.updateRecipe(request);
		ServiceResponse response = new ServiceResponse(RecipeUtil.buildServiceMessage(StatusEnum.STATUS_200));
		return new ResponseEntity<>(response, StatusEnum.STATUS_200.getHttpStatus());
	}
	
	@ApiOperation(value = "Delete the recipe")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Request completed successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "No recipe not found")})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ServiceResponse> deleteRecipe(@PathVariable(name = "id", required = true) int id) {
		log.info("Deleting the recipe by its id. Id: {}", id);
		recipeService.deleteRecipe(id);
		ServiceResponse response = new ServiceResponse(RecipeUtil.buildServiceMessage(StatusEnum.STATUS_200));
		return new ResponseEntity<>(response, StatusEnum.STATUS_200.getHttpStatus());
	}
	
	@ApiOperation(value = "Search recipes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Request completed successfully"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "No recipe found")})
	@PostMapping(path = "/search")
	public ResponseEntity<SearchRecipeResponse> searchRecipes(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "100") int size,
			@RequestBody @Valid SearchRecipeRequest recipeSearchRequest) {
		log.info("Searching the recipe by given criteria");
		SearchRecipeResponse response = recipeService.findRecipes(recipeSearchRequest, page, size);
		if (org.apache.commons.collections4.CollectionUtils.isEmpty(response.getRecipeList())) {
			throw new ResourceNotFound(messageProvider.getMessage("no.result.returned"));
		}
		response.setMessage(RecipeUtil.buildServiceMessage(StatusEnum.STATUS_200));
		return new ResponseEntity<>(response, StatusEnum.STATUS_200.getHttpStatus());

	}

}
