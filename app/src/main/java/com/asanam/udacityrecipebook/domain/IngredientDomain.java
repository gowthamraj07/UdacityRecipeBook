package com.asanam.udacityrecipebook.domain;

import android.content.ContentValues;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.dto.Ingredient;

public class IngredientDomain {
    private Ingredient ingredient;

    public IngredientDomain(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public ContentValues getContentValues(Integer recipeId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.IngredientsTable.COLUMN_INGREDIENT, ingredient.getIngredient());
        contentValues.put(DBContract.IngredientsTable.COLUMN_MEASURE, ingredient.getMeasure());
        contentValues.put(DBContract.IngredientsTable.COLUMN_QUANTITY, ingredient.getQuantity());
        contentValues.put(DBContract.IngredientsTable.COLUMN_RECIPE_ID, recipeId);
        return contentValues;
    }
}
