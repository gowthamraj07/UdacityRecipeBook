package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

public interface RecipeRepository {
    void getRecipes(Callback callback);

    public interface Callback {
        void onSuccess(Cursor domainList);
    }
}
