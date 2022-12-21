package com.abn.assessment.recipe.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.abn.assessment.recipe.model.request.CreateRecipeRequest;
import com.abn.assessment.recipe.service.MessageProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import test.object.builder.TestDataBuilder;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

class APIGlobalExceptionHandlerTest {
	@Autowired
	MockMvc mockMvc;
	@Mock
	MessageProvider messageProvider;
	
	ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();


	@Test
	void test_handleMethodArgumentNotValidException() throws Exception {
		CreateRecipeRequest request = TestDataBuilder.buildCreateRecipeRequest();
		request.setType(null);
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_handleHttpMediaTypeException() throws Exception {
		CreateRecipeRequest request = TestDataBuilder.buildCreateRecipeRequest();
		request.setName("milk-shake");
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_PDF)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().is(406));
	}
	
	@Test
	void test_handleHttpMessageNotReadableException() throws Exception {
		CreateRecipeRequest request = TestDataBuilder.buildCreateRecipeRequest();
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_PDF)
				.contentType(MediaType.APPLICATION_JSON)
				.content("sample"))
				.andExpect(status().is(400));
	}
	
	@Test
	void test_handleBadRequestException() throws Exception {
		CreateRecipeRequest request = TestDataBuilder.buildCreateRecipeRequest();
		request.setName("fries");
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().is(201));
		mockMvc.perform(post("/v1/recipe").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().is(400));
	}
	@Test
	void test_handleHttpMethodTypeException() throws Exception {
		mockMvc.perform(get("/v1/recipe"))
				.andExpect(status().is(405));
	}
	
	@Test
	void test_handleMethodArgumentTypeMismatchException() throws Exception {
		mockMvc.perform(get("/v1/recipe/one"))
				.andExpect(status().is(400));
	}
	@Test
	void test_handleNotFoundException() throws Exception {
		mockMvc.perform(get("/v1/recipe/25"))
				.andExpect(status().is(404));
	}

}
