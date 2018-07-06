package com.asanam.udacityrecipebook.presenter;

import android.database.Cursor;

import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.view.DetailsView;

public class DetailsPresenter {
    private DetailsView view;
    private DBRepository repository;

    public DetailsPresenter(DetailsView view, DBRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void getSteps(Long recipeId) {
        Cursor steps = repository.getSteps(recipeId);

        if(steps != null && steps.getCount() > 0) {
            view.showDetails(steps);
        }
    }
}
