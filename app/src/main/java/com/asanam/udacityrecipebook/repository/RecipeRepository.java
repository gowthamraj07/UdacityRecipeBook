package com.asanam.udacityrecipebook.repository;

public interface RecipeRepository {
    void getRecipies(Callback callback);

    public interface Callback {
    }
}
