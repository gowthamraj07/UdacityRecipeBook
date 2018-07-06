package com.asanam.udacityrecipebook.presenter;

import com.asanam.udacityrecipebook.domain.Recipe;
import com.asanam.udacityrecipebook.repository.RecipeRepository;
import com.asanam.udacityrecipebook.view.RecipeCardsView;

import java.util.List;

public class RecipeCardsPresenter {
    private RecipeCardsView view;
    private RecipeRepository repository;

    public RecipeCardsPresenter(RecipeCardsView view, RecipeRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void showCards() {
        RecipeRepository.Callback callback = new Callback();
        this.repository.getRecipies(callback);
    }

    private class Callback implements RecipeRepository.Callback {
        @Override
        public void onSuccess(List<Recipe> domainList) {
            if(domainList.isEmpty()) {
                view.showErrorMessage();
            } else {
                view.showCardsView(domainList);
            }
        }
    }
}
