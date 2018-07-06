package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.dto.RecipeListDto;

class RecipeDBRepository implements DBRepository {

    DBContract contract;

    @Override
    public Cursor saveRecipe(RecipeListDto spyDtoList) {
        return null;
    }
}
