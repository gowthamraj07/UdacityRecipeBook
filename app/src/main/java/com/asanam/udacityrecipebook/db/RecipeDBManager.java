package com.asanam.udacityrecipebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.asanam.udacityrecipebook.domain.IngredientDomain;
import com.asanam.udacityrecipebook.domain.Recipe;
import com.asanam.udacityrecipebook.domain.StepDomain;

import java.util.List;

public class RecipeDBManager extends RecipeDBHelper {
    public RecipeDBManager(Context context) {
        super(context);
    }

    public void addRecipes(List<Recipe> recipeList) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + DBContract.RecipeTable.TABLE_NAME);
        
        for(Recipe recipe : recipeList) {
            ContentValues contentValues = recipe.getContentValues();
            writableDatabase.insert(DBContract.RecipeTable.TABLE_NAME, null, contentValues);
            addIngredients(recipe.getIngredients(), recipe.getId());
            addSteps(recipe.getSteps(), recipe.getId());
        }
    }

    public Cursor queryAllRecipeNames() {
        String SELECT_QUERY = "SELECT " + DBContract.RecipeTable.COLUMN_NAME + " FROM " + DBContract.RecipeTable.TABLE_NAME;
        return getReadableDatabase().rawQuery(SELECT_QUERY, null);
    }

    public void addRecipes(ContentValues[] values) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + DBContract.RecipeTable.TABLE_NAME);

        for (ContentValues value : values) {
            writableDatabase.insert(DBContract.RecipeTable.TABLE_NAME, null, value);
        }
    }

    public void addIngredients(ContentValues[] values, Integer recipeId) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + DBContract.IngredientsTable.TABLE_NAME + " WHERE " +
                DBContract.IngredientsTable.COLUMN_RECIPE_ID + " = " + recipeId);

        for (ContentValues value : values) {
            writableDatabase.insert(DBContract.IngredientsTable.TABLE_NAME, null, value);
        }
    }

    public void addSteps(ContentValues[] values, Integer recipeId) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + DBContract.StepTable.TABLE_NAME + " WHERE " +
                DBContract.StepTable.COLUMN_RECIPE_ID + " = " + recipeId);

        for (ContentValues value : values) {
            writableDatabase.insert(DBContract.IngredientsTable.TABLE_NAME, null, value);
        }
    }


    private void addIngredients(List<IngredientDomain> ingredients, Integer recipeId) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + DBContract.IngredientsTable.TABLE_NAME + " WHERE " +
                DBContract.IngredientsTable.COLUMN_RECIPE_ID + " = " + recipeId);

        for(IngredientDomain ingredient : ingredients) {
            ContentValues contentValues = ingredient.getContentValues(recipeId);
            writableDatabase.insert(DBContract.IngredientsTable.TABLE_NAME, null, contentValues);
        }
    }

    private void addSteps(List<StepDomain> steps, Integer recipeId) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + DBContract.StepTable.TABLE_NAME + " WHERE " +
                DBContract.StepTable.COLUMN_RECIPE_ID + " = " + recipeId);

        for(StepDomain step : steps) {
            ContentValues contentValues = step.getContentValues(recipeId);
            writableDatabase.insert(DBContract.IngredientsTable.TABLE_NAME, null, contentValues);
        }
    }
}
