package com.abn.assessment.recipe.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abn.assessment.recipe.model.request.CreateRecipeRequest;
import com.abn.assessment.recipe.model.request.SearchRecipeRequest;
import com.abn.assessment.recipe.model.request.UpdateRecipeRequest;
import com.abn.assessment.recipe.model.response.RecipeResponse;
import com.abn.assessment.recipe.repository.RecipeRepository;
import com.abn.assessment.recipe.service.MessageProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;

import test.object.builder.TestDataBuilder;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RecipeControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	@Mock
	MessageProvider messageProvider;
	
	ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@BeforeEach
	public void before() {
		recipeRepository.deleteAll();
	}

	@Test
	void test_createRecipe() throws Exception {
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TestDataBuilder.buildCreateRecipeRequest())))
				.andExpect(status().is(201));
	}
	
	@Test
	void test_updateRecipe() throws Exception {
		CreateRecipeRequest createRecipeRequest = TestDataBuilder.buildCreateRecipeRequest();
		MvcResult mvcResult= mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createRecipeRequest)))
				.andExpect(status().is(201)).andReturn();
		Integer id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
		mvcResult = mockMvc.perform(get("/v1/recipe/" + id)).andExpect(status().isOk()).andReturn();
		
		RecipeResponse recipeResponse = getFromMvcResult(mvcResult, RecipeResponse.class);
		UpdateRecipeRequest updateRecipeRequest =TestDataBuilder.buildUpdateRecipeRequest();
		updateRecipeRequest.setId(recipeResponse.getId());
		
		mockMvc.perform(patch("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(TestDataBuilder.buildUpdateRecipeRequest())))
				.andExpect(status().is(200)).andReturn();
	}
	
	@Test
	void test_getRecipe() throws Exception {
		
		CreateRecipeRequest createRecipeRequest = TestDataBuilder.buildCreateRecipeRequest();
		MvcResult mvcResult= mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createRecipeRequest)))
				.andExpect(status().is(201)).andReturn();
		Integer id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
		mvcResult = mockMvc.perform(get("/v1/recipe/" + id)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void test_getRecipeList() throws Exception {
		CreateRecipeRequest createRecipeRequest = TestDataBuilder.buildCreateRecipeRequest();
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createRecipeRequest)))
				.andExpect(status().is(201)).andReturn();
		mockMvc.perform(get("/v1/recipe/page/0/size/10")).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	void test_searchRecipes() throws Exception {
		CreateRecipeRequest createRecipeRequest = TestDataBuilder.buildCreateRecipeRequest();
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createRecipeRequest)))
				.andExpect(status().is(201)).andReturn();
		SearchRecipeRequest searchRecipeRequest = TestDataBuilder.buildSearchRecipeRequest();
		searchRecipeRequest.getSearchCriteria().get(0).setFilterKey("name");
		searchRecipeRequest.getSearchCriteria().get(0).setOperation("eq");
		searchRecipeRequest.getSearchCriteria().get(0).setValue(createRecipeRequest.getName());
		mockMvc.perform(post("/v1/recipe/search").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(searchRecipeRequest))).andExpect(status().isOk());
	}
	
	@Test
	void test_deleteRecipe() throws Exception {
		
		CreateRecipeRequest createRecipeRequest = TestDataBuilder.buildCreateRecipeRequest();
		MvcResult mvcResult= mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createRecipeRequest)))
				.andExpect(status().is(201)).andReturn();
		Integer id = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
		mockMvc.perform(delete("/v1/recipe/" + id)).andExpect(status().isOk());
	}
	
	protected <T> T getFromMvcResult(MvcResult result, Class<T> objectClass) throws IOException {
		return objectMapper.readValue(result.getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructType(objectClass));
	}

}
