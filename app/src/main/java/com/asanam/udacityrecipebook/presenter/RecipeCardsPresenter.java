package com.asanam.udacityrecipebook.presenter;

import android.database.Cursor;

import com.asanam.udacityrecipebook.repository.RecipeRepository;
import com.asanam.udacityrecipebook.view.RecipeCardsView;

public class RecipeCardsPresenter {
    private RecipeCardsView view;
    private RecipeRepository repository;

    public RecipeCardsPresenter(RecipeCardsView view, RecipeRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void showCards() {
        RecipeRepository.Callback callback = new Callback();
        this.repository.getRecipes(callback);
    }

    private class Callback implements RecipeRepository.Callback {
        @Override
        public void onSuccess(Cursor domainList) {
            if(domainList.getCount() == 0) {
                view.showErrorMessage();
            } else {
                view.showCardsView(domainList);
            }
        }
    }
}
