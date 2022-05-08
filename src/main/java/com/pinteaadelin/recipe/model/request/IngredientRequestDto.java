package com.pinteaadelin.recipe.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
public class IngredientRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private Integer quantity;

}
