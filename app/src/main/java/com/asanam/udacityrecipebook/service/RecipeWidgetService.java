package com.asanam.udacityrecipebook.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.asanam.udacityrecipebook.provider.RecipeWidgetListProvider;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new RecipeWidgetListProvider(this.getApplicationContext(), intent));
    }
}
