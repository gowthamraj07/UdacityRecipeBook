package com.asanam.udacityrecipebook.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import com.asanam.udacityrecipebook.adapter.RecipeAdapter;

public class AndroidRecipeCardsView implements RecipeCardsView {
    private Context applicationContext;
    private RecyclerView rvRecipeCard;

    public AndroidRecipeCardsView(Context applicationContext, RecyclerView rvRecipeCard) {
        this.applicationContext = applicationContext;
        this.rvRecipeCard = rvRecipeCard;
    }

    @Override
    public void showCardsView(Cursor cursor) {
        RecipeAdapter adapter = new RecipeAdapter(applicationContext, cursor);

    }

    @Override
    public void showErrorMessage() {

    }
}
