package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

import com.asanam.udacityrecipebook.dto.RecipeListDto;

interface DBRepository {
    void saveRecipe(RecipeListDto spyDtoList);
    Cursor queryRecipeNames();
}
