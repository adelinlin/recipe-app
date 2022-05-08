package com.pinteaadelin.recipe.repository;

import com.pinteaadelin.recipe.model.Recipe;
import com.pinteaadelin.recipe.model.RecipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findAllByType(RecipeType type);

    List<Recipe> findAllByCreatedByIgnoreCase(String createdBy);

    List<Recipe> findAllByTypeAndNameIgnoreCaseAndCookingTimeAndDifficulty(
            String type, String name, Integer cookingTime, String difficulty);

    List<Recipe> findAllByTypeAndNameIgnoreCaseAndCookingTime(String type, String name, Integer cookingTime);

    List<Recipe> findAllByTypeAndNameIgnoreCaseAndDifficulty(String type, String name, String difficulty);

    List<Recipe> findAllByTypeAndCookingTimeAndDifficulty(String type, Integer cookingTime, String difficulty);

    List<Recipe> findAllByTypeAndNameIgnoreCase(String type, String name);

    List<Recipe> findAllByTypeAndCookingTime(String type, Integer cookingTime);

    List<Recipe> findAllByTypeAndDifficulty(String type, String difficulty);

    Optional<Recipe> findByIdAndTypeAndCreatedByIgnoreCase(Integer id, RecipeType type, String createdBy);

    Optional<Recipe> findByTypeAndNameIgnoreCase(RecipeType type, String name);

    void deleteByIdAndType(Integer id, RecipeType type);

}
