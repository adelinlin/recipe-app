package com.pinteaadelin.recipe.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "id")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RecipeType type;

    private Integer cookingTime;

    @Enumerated(EnumType.STRING)
    private RecipeDifficulty difficulty;

    private String createdBy;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recipes_recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_ingredients_id", referencedColumnName = "id"))
    private List<RecipeIngredient> recipeIngredients;
}
