package com.asanam.udacityrecipebook.repository;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.asanam.udacityrecipebook.domain.IngredientDomain;
import com.asanam.udacityrecipebook.domain.Recipe;
import com.asanam.udacityrecipebook.domain.StepDomain;
import com.asanam.udacityrecipebook.dto.Ingredient;
import com.asanam.udacityrecipebook.dto.RecipeDto;
import com.asanam.udacityrecipebook.dto.RecipeListDto;
import com.asanam.udacityrecipebook.dto.Step;
import com.asanam.udacityrecipebook.network.NetworkApi;
import com.asanam.udacityrecipebook.provider.RecipeProvider;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class NetworkRecipeRepository implements RecipeRepository {
    private static final String RECIPE_URL = "";
    private NetworkApi api;
    private ContentResolver contentResolver;
    private NetworkRepositoryCallback networkCallback;
    private Callback callback;


    public NetworkRecipeRepository(NetworkApi api, ContentResolver contentResolver, NetworkRepositoryCallback networkCallback) {
        this.api = api;
        this.contentResolver = contentResolver;
        this.networkCallback = networkCallback;
    }

    @Override
    public void getRecipes(Callback callback) {
        this.callback = callback;
        NetworkApi.Callback apiCallback = new NetworkCallback();
        api.get("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json", apiCallback);
    }

    private class NetworkCallback implements NetworkApi.Callback {
        @Override
        public void onSuccess(String jsonResponse) {
            GsonBuilder builder = new GsonBuilder();
            List<RecipeDto> recipeDtos = builder.create().fromJson(jsonResponse, new TypeToken<List<RecipeDto>>() {
            }.getType());
            RecipeListDto recipeListDto = new RecipeListDto(recipeDtos);
            if (!recipeListDto.getRecipeDtoList().isEmpty()) {
                //dbRepository.saveRecipe(recipeListDto);
                ContentValues[] contentValues = new ContentValues[recipeListDto.getRecipeDtoList().size()];
                int index = 0;
                for (RecipeDto recipeDto : recipeListDto.getRecipeDtoList()) {
                    contentValues[index] = new Recipe(recipeDto).getContentValues();

                    insertRecipeStepToDB(recipeListDto, index, recipeDto);
                    insertIngredientsToDB(recipeListDto, index, recipeDto);

                    index++;
                }

                contentResolver.bulkInsert(RecipeProvider.RECIPE_NAME_URI, contentValues);
            }
            callback.onSuccess(contentResolver.query(RecipeProvider.RECIPE_NAME_URI, null,null,null,null));
            networkCallback.onSuccess();
        }
    }

    private void insertRecipeStepToDB(RecipeListDto recipeListDto, int index, RecipeDto recipeDto) {
        ContentValues[] stepValues = new ContentValues[recipeListDto.getRecipeDtoList().get(index).getSteps().size()];
        int stepIndex = 0;
        for(Step step : recipeListDto.getRecipeDtoList().get(index).getSteps()) {
            stepValues[stepIndex++] = new StepDomain(step).getContentValues(recipeDto.getId());
        }
        contentResolver.bulkInsert(RecipeProvider.STEP_URI.buildUpon().appendPath(""+recipeDto.getId()).build(), stepValues);
    }

    private void insertIngredientsToDB(RecipeListDto recipeListDto, int index, RecipeDto recipeDto) {
        ContentValues[] ingredients = new ContentValues[recipeListDto.getRecipeDtoList().get(index).getIngredients().size()];
        int stepIndex = 0;
        for(Ingredient ingredient : recipeListDto.getRecipeDtoList().get(index).getIngredients()) {
            ingredients[stepIndex++] = new IngredientDomain(ingredient).getContentValues(recipeDto.getId());
        }
        contentResolver.bulkInsert(RecipeProvider.INGREDIENTS_URI.buildUpon().appendPath(""+recipeDto.getId()).build(), ingredients);
    }

    public interface NetworkRepositoryCallback {
        void onSuccess();
    }
}
