package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

import com.asanam.udacityrecipebook.dto.RecipeListDto;

import java.util.List;

interface DBRepository {
    Cursor saveRecipe(RecipeListDto spyDtoList);
}
