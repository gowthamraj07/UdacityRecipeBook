package com.asanam.udacityrecipebook.presenter;

import com.asanam.udacityrecipebook.repository.RecipeRepository;

public class RecipeCardsPresenter {
    private RecipeRepository repository;

    public RecipeCardsPresenter(RecipeRepository repository) {
        this.repository = repository;
    }

    public void showCards() {
        RecipeRepository.Callback callback = new RecipeCallback();
        this.repository.getRecipies(callback);
    }

    private class RecipeCallback implements RecipeRepository.Callback {
    }
}
