package test.object.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

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
import com.abn.assessment.recipe.repository.entity.Recipe;
import com.abn.assessment.recipe.util.RecipeUtil;

public class TestDataBuilder {

	public static Recipe buildRecipe() {
		List<IngredientReq> ingredientsList = new ArrayList<IngredientReq>();
		ingredientsList.add(buildIngredientReq());
		Recipe obj = new Recipe();
		obj.setCreatedAt(LocalDateTime.now());
		obj.setId(1);
		obj.setIngredients(RecipeUtil.convertToJSONString(ingredientsList));
		obj.setInstructions("instructions");
		obj.setLastUpdatedAt(LocalDateTime.now());
		obj.setName("sandwich");
		obj.setNumberOfServings(2);
		obj.setType("vegetarian");
		return obj;
	}
	
	public static ServiceMessage buildServiceMessage() {
		ServiceMessage response = new ServiceMessage();
		response.setDescription("Request Completed Successfully.");
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("success");
		return response;
	}

	public static CreateRecipeResponse buildCreateRecipeResponse() {
		CreateRecipeResponse response = new CreateRecipeResponse();
		response.setId(1);
		response.setName("sandwich");
		response.setMessage(buildServiceMessage());
		return response;
	}

	public static CreateRecipeRequest buildCreateRecipeRequest() {
		List<IngredientReq> ingredientsList = new ArrayList<IngredientReq>();
		ingredientsList.add(buildIngredientReq());
		CreateRecipeRequest request = new CreateRecipeRequest();
		request.setIngredients(ingredientsList);
		request.setInstructions("instructions");
		request.setName("sandwich");
		request.setNumberOfServings(2);
		request.setType("vegetarian");
		return request;
	}

	public static UpdateRecipeRequest buildUpdateRecipeRequest() {
		List<IngredientReq> ingredientsList = new ArrayList<IngredientReq>();
		ingredientsList.add(buildIngredientReq());
		UpdateRecipeRequest request = new UpdateRecipeRequest();
		request.setId(1);
		request.setIngredients(ingredientsList);
		request.setInstructions("instructions");
		request.setName("sandwich");
		request.setNumberOfServings(2);
		request.setType("vegetarian");
		return request;
	}

	public static RecipeResponse buildRecipeResponse() {
		List<IngredientRes> ingredientsList = new ArrayList<IngredientRes>();
		ingredientsList.add(buildIngredientRes());
		RecipeResponse response = new RecipeResponse();
		response.setId(1);
		response.setIngredients(ingredientsList);
		response.setInstructions("instructions");
		response.setName("sandwich");
		response.setNumberOfServings(2);
		response.setType("vegetarian");
		response.setCreatedAt(LocalDateTime.now());
		response.setLastUpdatedAt(LocalDateTime.now());
		return response;
	}

	public static SearchRecipeResponse buildSearchRecipeResponse() {
		List<RecipeResponse> recipeResponseList = new ArrayList<RecipeResponse>();
		recipeResponseList.add(buildRecipeResponse());
		SearchRecipeResponse response = new SearchRecipeResponse();
		response.setMessage(buildServiceMessage());
		response.setRecipeList(recipeResponseList);
		return response;
	}

	public static SearchCriteria buildSearchCriteria() {
		SearchCriteria request = new SearchCriteria();
		request.setFilterKey("name");
		request.setOperation("eq");
		request.setValue("sandwich");
		return request;
	}

	public static SearchRecipeRequest buildSearchRecipeRequest() {
		List<SearchCriteria> searchCriteriaList = new ArrayList<SearchCriteria>();
		searchCriteriaList.add(buildSearchCriteria());
		SearchRecipeRequest request = new SearchRecipeRequest();
		request.setSearchCriteria(searchCriteriaList);
		return request;
	}

	public static IngredientReq buildIngredientReq() {
		IngredientReq request = new IngredientReq();
		request.setName("Bread");
		request.setQuantity("1");
		return request;
	}

	public static IngredientRes buildIngredientRes() {
		IngredientRes response = new IngredientRes();
		response.setName("Bread");
		response.setQuantity("1");
		return response;
	}

}
