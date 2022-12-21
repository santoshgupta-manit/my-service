package com.abn.assessment.recipe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.abn.assessment.recipe.exception.ResourceNotFound;
import com.abn.assessment.recipe.model.request.CreateRecipeRequest;
import com.abn.assessment.recipe.model.request.SearchCriteria;
import com.abn.assessment.recipe.model.request.SearchRecipeRequest;
import com.abn.assessment.recipe.model.request.UpdateRecipeRequest;
import com.abn.assessment.recipe.model.response.CreateRecipeResponse;
import com.abn.assessment.recipe.model.response.RecipeResponse;
import com.abn.assessment.recipe.model.response.SearchRecipeResponse;
import com.abn.assessment.recipe.repository.RecipeRepository;
import com.abn.assessment.recipe.repository.entity.Recipe;
import com.abn.assessment.recipe.search.RecipeSpecificationBuilder;
import com.abn.assessment.recipe.util.RecipeUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final MessageProvider messageProvider;

	public RecipeService(RecipeRepository recipeRepository, MessageProvider messageProvider) {
		super();
		this.recipeRepository = recipeRepository;
		this.messageProvider = messageProvider;
	}

	public CreateRecipeResponse createRecipe(CreateRecipeRequest req) {
		log.debug("input param req: {} ",req.toString());
		CreateRecipeResponse createRecipeResponse ;
		if (null == recipeRepository.findByNameIgnoreCase(req.getName())) {
			Recipe recipe = new Recipe();
			recipe.setName(req.getName());
			recipe.setIngredients(RecipeUtil.convertToJSONString(req.getIngredients()));
			recipe.setInstructions(req.getInstructions());
			recipe.setNumberOfServings(req.getNumberOfServings());
			recipe.setType(req.getType());
			Recipe recipeObj = recipeRepository.save(recipe);
		
			createRecipeResponse =  new CreateRecipeResponse(recipeObj.getId(), recipeObj.getName());
		} else {
			throw new IllegalArgumentException(messageProvider.getMessage("recipe.alreadyPresent"));
		}
		log.debug("output param createRecipeResponse: {} ",createRecipeResponse.toString());
		return createRecipeResponse;
	}

	public void updateRecipe(UpdateRecipeRequest req) {
		log.debug("input param req: {} ",req.toString());
		Recipe recipe = recipeRepository.findById(req.getId())
				.orElseThrow(() -> new IllegalArgumentException(messageProvider.getMessage("recipe.notFound")));
		recipe.setName(req.getName());
		recipe.setType(req.getType());
		recipe.setNumberOfServings(req.getNumberOfServings());
		recipe.setInstructions(req.getInstructions());
		recipe.setIngredients(RecipeUtil.convertToJSONString(req.getIngredients()));
		recipeRepository.save(recipe);
	}

	public void deleteRecipe(int id) {
		log.debug("input param req: {} ",id);
		if (!recipeRepository.existsById(id)) {
			throw new ResourceNotFound(messageProvider.getMessage("recipe.notFound"));
		} else {
			recipeRepository.deleteById(id);
		}
		
	}

	public RecipeResponse getRecipeById(int id) {
		log.debug("input param req: {} ",id);
		RecipeResponse recipeResponse;
		Optional<Recipe> recipe = recipeRepository.findById(id);
		if (recipe.isPresent()) {
			recipeResponse =  new RecipeResponse(recipe.get());
		} else {
			throw new ResourceNotFound(messageProvider.getMessage("recipe.notFound"));
		}
		
		log.debug("output param recipeResponse: {} ",recipeResponse.toString());
		return recipeResponse;
	}

	public SearchRecipeResponse getRecipeList(int page, int size) {
		log.debug("input param page: {}, size: {} ",page,size);
		Pageable pageRequest = PageRequest.of(page, size);
		SearchRecipeResponse searchRecipeRes = new SearchRecipeResponse();
		searchRecipeRes.setRecipeList(recipeRepository.findAll(pageRequest).getContent().stream().map(RecipeResponse::new)
				.collect(Collectors.toList()));
		log.debug("output param recipeResponse: {} ",searchRecipeRes.toString());
		return searchRecipeRes;

	}

	public SearchRecipeResponse findRecipes(SearchRecipeRequest recipeSearchRequest, int page, int size) {
		log.debug("input param recipeSearchRequest: {}, page: {}, size: {}",recipeSearchRequest.toString(),page,size);
		SearchRecipeResponse searchRecipeRes = new SearchRecipeResponse();
		List<SearchCriteria> searchCriterionList = new ArrayList<>();
		RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(searchCriterionList);
		Pageable pageRequest = PageRequest.of(page, size, Sort.by("name").ascending());
		Specification<Recipe> recipeSpecification = createRecipeSpecification(recipeSearchRequest, builder);
		searchRecipeRes.setRecipeList(recipeRepository.findAll(recipeSpecification, pageRequest).getContent().stream()
				.map(RecipeResponse::new).collect(Collectors.toList()));
		log.debug("output param recipeResponse: {} ",searchRecipeRes.toString());
		return searchRecipeRes;
	}

	private Specification<Recipe> createRecipeSpecification(SearchRecipeRequest recipeSearchRequest,
			RecipeSpecificationBuilder builder) {
		if (!recipeSearchRequest.getSearchCriteria().isEmpty())
			recipeSearchRequest.getSearchCriteria().forEach(builder::with);
		return builder.build()
				.orElseThrow(() -> new ResourceNotFound(messageProvider.getMessage("query.criteria.notFound")));
	}

}
