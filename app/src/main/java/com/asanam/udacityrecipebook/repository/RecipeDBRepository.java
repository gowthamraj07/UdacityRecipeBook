package com.asanam.udacityrecipebook.repository;

import android.content.Context;
import android.database.Cursor;

import com.asanam.udacityrecipebook.db.RecipeDBManager;
import com.asanam.udacityrecipebook.domain.Recipe;
import com.asanam.udacityrecipebook.dto.RecipeDto;
import com.asanam.udacityrecipebook.dto.RecipeListDto;
import com.asanam.udacityrecipebook.provider.RecipeProvider;

import java.util.ArrayList;
import java.util.List;

public class RecipeDBRepository implements DBRepository {

    private Context context;

    public RecipeDBRepository(Context context) {
        this.context = context;
    }

    @Override
    public void saveRecipe(RecipeListDto dtoList) {

    }

    @Override
    public Cursor queryRecipeNames() {
        return context.getContentResolver().query(RecipeProvider.RECIPE_NAME_URI, null, null, null, null);
    }

    @Override
    public Cursor getSteps(Long recipeId) {
        return context.getContentResolver().query(RecipeProvider.STEP_URI.buildUpon().appendPath(""+recipeId).build(), null, null, null, null);
    }
}
