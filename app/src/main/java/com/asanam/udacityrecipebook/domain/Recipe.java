package com.asanam.udacityrecipebook.domain;

import com.asanam.udacityrecipebook.dto.Ingredient;
import com.asanam.udacityrecipebook.dto.RecipeDto;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private RecipeDto dto;

    public Recipe(RecipeDto dto) {
        this.dto = dto;
    }

    public String getName() {
        return dto.getName();
    }

    public List<IngredientDomain> getIngredients() {
        List<IngredientDomain> ingredientDomains = new ArrayList<>();
        for(Ingredient ingredient : dto.getIngredients()) {
            IngredientDomain ingredientDomain = new IngredientDomain(ingredient);
            ingredientDomains.add(ingredientDomain);
        }
        return ingredientDomains;
    }
}
