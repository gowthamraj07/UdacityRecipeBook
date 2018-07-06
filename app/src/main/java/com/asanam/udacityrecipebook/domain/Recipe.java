package com.asanam.udacityrecipebook.domain;

import android.content.ContentValues;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.dto.Ingredient;
import com.asanam.udacityrecipebook.dto.RecipeDto;
import com.asanam.udacityrecipebook.dto.Step;

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

    public Integer getServings() {
        return dto.getServings();
    }

    public String getImage() {
        return dto.getImage();
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.RecipeTable.COLUMN_IMAGE, dto.getImage());
        contentValues.put(DBContract.RecipeTable.COLUMN_NAME, dto.getName());
        contentValues.put(DBContract.RecipeTable.COLUMN_SERVINGS, dto.getServings());
        contentValues.put(DBContract.RecipeTable.COLUMN_RECIPE_ID, dto.getId());
        return contentValues;
    }

    public Integer getId() {
        return dto.getId();
    }

    public List<StepDomain> getSteps() {
        List<StepDomain> stepDomains = new ArrayList<>();
        for (Step step : dto.getSteps()) {
            StepDomain domain = new StepDomain(step);
            stepDomains.add(domain);
        }

        return stepDomains;
    }
}
