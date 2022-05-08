package com.pinteaadelin.recipe.service;

import com.pinteaadelin.recipe.model.RecipeIngredient;
import com.pinteaadelin.recipe.model.request.IngredientRequestDto;
import com.pinteaadelin.recipe.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public RecipeIngredient findOrInsert(IngredientRequestDto ingredient) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setName(ingredient.getName());
        recipeIngredient.setQuantity(ingredient.getQuantity());

        return recipeIngredientRepository.findByNameIgnoreCaseAndQuantity(ingredient.getName(), ingredient.getQuantity())
                .orElseGet(() -> recipeIngredientRepository.save(recipeIngredient));
    }

}
