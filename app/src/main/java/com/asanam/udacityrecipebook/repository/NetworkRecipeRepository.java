package com.asanam.udacityrecipebook.repository;

import com.asanam.udacityrecipebook.dto.RecipeDto;
import com.asanam.udacityrecipebook.dto.RecipeListDto;
import com.asanam.udacityrecipebook.network.NetworkApi;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

class NetworkRecipeRepository implements RecipeRepository {
    private static final String RECIPE_URL = "";
    private NetworkApi api;
    private DBRepository dbRepository;

    public NetworkRecipeRepository(NetworkApi api, DBRepository dbRepository) {
        this.api = api;
        this.dbRepository = dbRepository;
    }

    @Override
    public void getRecipes(Callback callback) {
        String jsonResponse = api.get(RECIPE_URL);
        GsonBuilder builder = new GsonBuilder();
        List<RecipeDto> recipeDtos = builder.create().fromJson(jsonResponse, new TypeToken<List<RecipeDto>>() {
        }.getType());
        RecipeListDto recipeListDto = new RecipeListDto(recipeDtos);
        if (!recipeListDto.getRecipeDtoList().isEmpty()) {
            dbRepository.saveRecipe(recipeListDto);
        }

        callback.onSuccess(dbRepository.queryRecipeNames());
    }
}
