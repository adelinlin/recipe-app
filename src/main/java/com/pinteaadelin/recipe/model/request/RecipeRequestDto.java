package com.pinteaadelin.recipe.model.request;

import com.pinteaadelin.recipe.model.RecipeDifficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class RecipeRequestDto {

    @NotBlank
    private String name;

    @Positive
    private Integer cookingTime;

    @NotNull
    private RecipeDifficulty difficulty;

    @NotEmpty
    private List<IngredientRequestDto> ingredients;

}
