package com.asanam.udacityrecipebook.provider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.RecipeWidget;
import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecipeWidgetListProvider implements RemoteViewsService.RemoteViewsFactory {
    public static final String TAG = RecipeWidgetListProvider.class.getSimpleName();
    private List<String> ingredients = new ArrayList<>();
    private final Context applicationContext;
    private Intent intent;

    public RecipeWidgetListProvider(Context applicationContext, Intent intent) {
        this.applicationContext = applicationContext;
        this.intent = intent;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        long recipeId = intent.getLongExtra(Constants.RECIPE_ID, RecipeWidget.recipeId);
        Log.d(TAG, "onDataSetChanged: "+recipeId);
        Cursor query = applicationContext.getContentResolver().query(
                RecipeProvider.INGREDIENTS_URI.buildUpon().appendPath("" + recipeId).build(),
                null, null, null, null
        );
        ingredients = new ArrayList<>();
        while(query.moveToNext()) {
            ingredients.add(query.getString(query.getColumnIndex(DBContract.IngredientsTable.COLUMN_INGREDIENT)));
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteView = new RemoteViews(applicationContext.getPackageName(), R.layout.widget_list_item);
        remoteView.setTextViewText(R.id.tv_widget_list_item, ingredients.get(i));
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
