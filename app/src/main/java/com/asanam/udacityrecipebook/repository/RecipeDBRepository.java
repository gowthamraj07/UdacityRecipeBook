package com.asanam.udacityrecipebook.repository;

import android.content.Context;
import android.database.Cursor;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.dto.RecipeListDto;
import com.asanam.udacityrecipebook.provider.RecipeProvider;

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

    @Override
    public Cursor getStepDetails(Long stepId, Long recipeId) {
        return context.getContentResolver().query(RecipeProvider.STEP_DETAILS_URI.buildUpon().appendPath(""+recipeId).build(), null, DBContract.StepTable.COLUMN_ID+" = "+stepId, null, null);
    }

    @Override
    public Cursor getPreviousStepDetails(Long recipeId, Long stepId) {

        return null;
    }
}
