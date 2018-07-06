package com.asanam.udacityrecipebook.repository;

import com.asanam.udacityrecipebook.network.NetworkApi;

class NetworkRecipeRepository implements RecipeRepository {
    public static final String RECIPE_URL = "";
    private NetworkApi api;

    public NetworkRecipeRepository(NetworkApi api) {
        this.api = api;
    }

    @Override
    public void getRecipes(Callback callback) {
        api.get(RECIPE_URL);
    }
}
