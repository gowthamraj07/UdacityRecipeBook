package com.asanam.udacityrecipebook.view;

import android.database.Cursor;

public interface RecipeCardsView {
    void showCardsView(Cursor spyDomainList);
    void showErrorMessage();
}
