package com.asanam.udacityrecipebook.repository;

import android.content.Context;
import android.database.Cursor;

import com.asanam.udacityrecipebook.db.RecipeDBManager;
import com.asanam.udacityrecipebook.domain.Recipe;
import com.asanam.udacityrecipebook.dto.RecipeDto;
import com.asanam.udacityrecipebook.dto.RecipeListDto;

import java.util.ArrayList;
import java.util.List;

class RecipeDBRepository implements DBRepository {

    private final RecipeDBManager manager;

    public RecipeDBRepository(Context context) {
        manager = new RecipeDBManager(context);
    }

    @Override
    public Cursor saveRecipe(RecipeListDto dtoList) {

        List<Recipe> recipes = new ArrayList<>();
        for (RecipeDto recipeDto : dtoList.getRecipeDtoList()) {
            recipes.add(new Recipe(recipeDto));
        }

        manager.addRecipes(recipes);
        return manager.queryAllRecipeNames();
    }
}
