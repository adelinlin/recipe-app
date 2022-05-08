package com.pinteaadelin.recipe.service;

import com.pinteaadelin.recipe.exception.AlreadyExistsException;
import com.pinteaadelin.recipe.exception.NotFoundException;
import com.pinteaadelin.recipe.model.Recipe;
import com.pinteaadelin.recipe.model.RecipeDifficulty;
import com.pinteaadelin.recipe.model.RecipeIngredient;
import com.pinteaadelin.recipe.model.RecipeType;
import com.pinteaadelin.recipe.model.request.RecipeRequestDto;
import com.pinteaadelin.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;

    private final RecipeIngredientService recipeIngredientService;

    public RecipeService(RecipeRepository recipeRepository, RecipeIngredientService recipeIngredientService) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientService = recipeIngredientService;
    }

    public List<Recipe> getPublicRecipes(String name, Integer cookingTime, RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndNameIgnoreCaseAndCookingTimeAndDifficulty(
                RecipeType.PUBLIC.name(), name, cookingTime, difficulty.name());
    }

    public List<Recipe> getPublicRecipes(String name, Integer cookingTime) {
        return recipeRepository.findAllByTypeAndNameIgnoreCaseAndCookingTime(RecipeType.PUBLIC.name(), name, cookingTime);
    }

    public List<Recipe> getPublicRecipes(String name, RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndNameIgnoreCaseAndDifficulty(RecipeType.PUBLIC.name(), name, difficulty.name());
    }

    public List<Recipe> getPublicRecipes(Integer cookingTime, RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndCookingTimeAndDifficulty(RecipeType.PUBLIC.name(), cookingTime, difficulty.name());
    }

    public List<Recipe> getPublicRecipes(String name) {
        return recipeRepository.findAllByTypeAndNameIgnoreCase(RecipeType.PUBLIC.name(), name);
    }

    public List<Recipe> getPublicRecipes(Integer cookingTime) {
        return recipeRepository.findAllByTypeAndCookingTime(RecipeType.PUBLIC.name(), cookingTime);
    }

    public List<Recipe> getPublicRecipes(RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndDifficulty(RecipeType.PUBLIC.name(), difficulty.name());
    }

    public List<Recipe> getPublicRecipes() {
        return recipeRepository.findAllByType(RecipeType.PUBLIC);
    }

    public List<Recipe> getLoggedUserRecipes(String username) {
        List<Recipe> loggedUserRecipes = recipeRepository.findAllByCreatedByIgnoreCase(username);
        List<Recipe> publicRecipes = getPublicRecipes();

        return publicRecipes.addAll(loggedUserRecipes) ? publicRecipes : loggedUserRecipes;
    }

    public Recipe createRecipe(RecipeRequestDto recipeRequestDto, RecipeType recipeType, String username) {
        Optional<Recipe> optionalRecipe =
                recipeRepository.findByTypeAndNameIgnoreCase(recipeType, recipeRequestDto.getName());
        if (optionalRecipe.isPresent()) {
            throw new AlreadyExistsException(format("{0} {1} recipe already exists",
                    recipeType.name(), recipeRequestDto.getName()));
        }
        Recipe recipe = Recipe.builder()
                .name(recipeRequestDto.getName())
                .type(recipeType)
                .cookingTime(recipeRequestDto.getCookingTime())
                .difficulty(recipeRequestDto.getDifficulty())
                .recipeIngredients(getIngredients(recipeRequestDto))
                .createdBy(username)
                .build();

        return recipeRepository.save(recipe);
    }

    private List<RecipeIngredient> getIngredients(RecipeRequestDto recipeRequestDto) {
        return recipeRequestDto.getIngredients().stream()
                .map(recipeIngredientService::findOrInsert)
                .collect(toList());
    }

    public Recipe updateRecipe(Integer id, RecipeRequestDto recipeRequestDto, RecipeType recipeType, String username) {
        Recipe recipe = findValidRecipe(id, recipeType, username);
        Recipe updatedRecipe = recipe.toBuilder()
                .name(recipeRequestDto.getName())
                .difficulty(recipeRequestDto.getDifficulty())
                .cookingTime(recipeRequestDto.getCookingTime())
                .recipeIngredients(getIngredients(recipeRequestDto))
                .build();

        return recipeRepository.save(updatedRecipe);
    }

    public void deleteRecipe(Integer id, RecipeType recipeType, String username) {
        findValidRecipe(id, recipeType, username);

        recipeRepository.deleteByIdAndType(id, recipeType);
    }

    private Recipe findValidRecipe(Integer id, RecipeType recipeType, String username) {
        Optional<Recipe> optionalRecipe =
                recipeRepository.findByIdAndTypeAndCreatedByIgnoreCase(id, recipeType, username);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException(format("A {0} recipe with id {1} does not exist for user {2}",
                    recipeType.name(), id, username));
        }

        return optionalRecipe.get();
    }

}
