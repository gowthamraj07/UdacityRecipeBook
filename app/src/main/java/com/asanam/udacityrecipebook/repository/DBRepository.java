package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

import com.asanam.udacityrecipebook.dto.RecipeListDto;

public interface DBRepository {
    void saveRecipe(RecipeListDto spyDtoList);
    Cursor queryRecipeNames();

    Cursor getSteps(Long recipeId);
    Cursor getStepDetails(Long stepId, Long recipeId);
}
