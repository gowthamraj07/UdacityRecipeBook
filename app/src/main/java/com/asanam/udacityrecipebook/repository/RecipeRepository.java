package com.asanam.udacityrecipebook.repository;

import com.asanam.udacityrecipebook.domain.Recipe;

import java.util.List;

public interface RecipeRepository {
    void getRecipies(Callback callback);

    public interface Callback {
        void onSuccess(List<Recipe> domainList);
    }
}
