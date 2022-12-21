package com.abn.assessment.recipe.util;

import java.util.Arrays;
import java.util.List;

import com.abn.assessment.recipe.enums.StatusEnum;
import com.abn.assessment.recipe.model.request.IngredientReq;
import com.abn.assessment.recipe.model.response.IngredientRes;
import com.abn.assessment.recipe.model.response.ServiceMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RecipeUtil {
	

	private RecipeUtil() {
		super();
	}

	public static String convertToJSONString(List<IngredientReq> ingList) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(ingList);
		} catch (Exception e) {
			log.error("Exception caught while converting List to JSON String");
		}
		return jsonString;
	}

	// Convert given JSON String to List of Ingredients
	public static List<IngredientRes> convertToIngredientsList(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		List<IngredientRes> ingredientsList = null;
		try {
			// Convert JSON array to List of objects
			ingredientsList = Arrays.asList(mapper.readValue(jsonString, IngredientRes[].class));
		} catch (Exception e) {
			log.error("Exception caught while converting JSON String to Ingredients List");
		}
		return ingredientsList;
	}
	
	public static ServiceMessage buildServiceMessage(StatusEnum statusEnum) {
		return new ServiceMessage(statusEnum.getHttpStatus().getReasonPhrase(), statusEnum.getHttpStatus().value(),
				statusEnum.getDescription());
	}

}
