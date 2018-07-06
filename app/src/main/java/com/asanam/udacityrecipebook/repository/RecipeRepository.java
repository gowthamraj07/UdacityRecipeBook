package com.asanam.udacityrecipebook.repository;

import android.database.Cursor;

public interface RecipeRepository {
    void getRecipies(Callback callback);

    public interface Callback {
        void onSuccess(Cursor domainList);
    }
}
