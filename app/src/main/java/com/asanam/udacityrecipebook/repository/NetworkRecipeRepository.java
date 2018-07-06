package com.asanam.udacityrecipebook.repository;

import com.asanam.udacityrecipebook.dto.RecipeListDto;
import com.asanam.udacityrecipebook.network.NetworkApi;
import com.google.gson.GsonBuilder;

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
        RecipeListDto recipeListDto = builder.create().fromJson(jsonResponse, RecipeListDto.class);
        if(recipeListDto != null) {
            dbRepository.saveRecipe(recipeListDto);
        }
    }
}
