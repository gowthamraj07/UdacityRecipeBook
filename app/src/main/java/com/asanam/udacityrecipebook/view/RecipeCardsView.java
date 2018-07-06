package com.asanam.udacityrecipebook.view;

import com.asanam.udacityrecipebook.domain.Recipe;

import java.util.List;

public interface RecipeCardsView {
    void showCardsView(List<Recipe> spyDomainList);
}
