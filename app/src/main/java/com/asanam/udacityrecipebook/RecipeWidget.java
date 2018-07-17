package com.asanam.udacityrecipebook;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.provider.RecipeProvider;
import com.asanam.udacityrecipebook.utils.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public static final String TAG = "RecipeWidget";
    public static final String RECIPE_ACTION = "RECIPE_ACTION";
    public static final String NEXT = "NEXT";
    public static final String PREVIOUS = "PREVIOUS";
    public static final String ACTION = "com.asanam.udacityrecipebook.ACTION";

    private static Long recipeId = -1L;
    private static boolean isAfterLast;

    private RemoteViews views;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        Log.d(TAG, "updateAppWidget: ");

        if(recipeId == -1) {
            Cursor query = context.getContentResolver().query(RecipeProvider.RECIPE_NAME_URI, null, null, null, null);
            if(query != null && query.getCount() > 0) {
                query.moveToFirst();
                recipeId = query.getLong(query.getColumnIndex(DBContract.RecipeTable.COLUMN_RECIPE_ID));
                query.close();
            }
        }

        if (!setWidgetValues(context, recipeId)) {
            return;
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.RECIPE_ID, recipeId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.tv_recipe_title, pendingIntent);

        int[] ids = new int[1];
        ids[0] = appWidgetId;

        Intent nextIntent = new Intent(context, getClass());
        nextIntent.setAction(ACTION);
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        nextIntent.putExtra(RECIPE_ACTION, NEXT);
        PendingIntent pendingNextIntent = PendingIntent.getBroadcast(context, 1235, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_next, pendingNextIntent);

        Intent previousIntent = new Intent(context, getClass());
        previousIntent.setAction(ACTION);
        previousIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        previousIntent.putExtra(RECIPE_ACTION, PREVIOUS);
        PendingIntent pendingPreviousIntent = PendingIntent.getBroadcast(context, 1236, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_previous, pendingPreviousIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private boolean setWidgetValues(Context context, Long recipeId) {

        Log.d(TAG, "setWidgetValues: recipeId = " + recipeId);

        Cursor cursor = context.getContentResolver().query(RecipeProvider.RECIPE_NAME_URI, null, null, null, null);

        if(cursor == null) {
            return false;
        }

        //To find the recipe id from DB to display
        if (recipeId != null) {
            while (cursor.moveToNext()) {
                long cursorRecipeId = cursor.getLong(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_RECIPE_ID));
                if (cursorRecipeId >= recipeId) {
                    isAfterLast = false;
                    break;
                }
            }
        } else {
            cursor.moveToFirst();
        }

        if (cursor.isAfterLast()) {
            isAfterLast = true;
            return false;
        }

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        String ingredientString = "";

        if (cursor.getCount() > 0) {
            long aLong = cursor.getLong(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_RECIPE_ID));
            widgetText = cursor.getString(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_NAME));

            Cursor ingredients = context.getContentResolver().query(RecipeProvider.INGREDIENTS_URI.buildUpon().appendPath("" + aLong).build(), null, null, null, null);
            StringBuilder builder = new StringBuilder();

            if( ingredients != null) {
                while (ingredients.moveToNext()) {
                    String ingredientsString = ingredients.getString(ingredients.getColumnIndex(DBContract.IngredientsTable.COLUMN_INGREDIENT));
                    builder.append(ingredientsString).append("\n");
                }
                ingredients.close();
            }

            cursor.close();
            ingredientString = builder.toString();
        }

        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.tv_recipe_title, widgetText);
        views.setTextViewText(R.id.lv_ingredients, ingredientString);

        return true;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: recipeId : " + recipeId);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        String stringExtra = intent.getStringExtra(RECIPE_ACTION);
        Log.d(TAG, "onReceive: recipeId : " + recipeId);
        Log.d(TAG, "onReceive: stringExtra : " + stringExtra);

        if (stringExtra == null) {
            return;
        }

        if (NEXT.equals(stringExtra)) {
            if(isAfterLast) {
                --recipeId; //Setting to previous position
            } else {
                ++recipeId;
            }
        } else {
            recipeId = recipeId == 0 ? 0 : --recipeId;
        }

        ComponentName thisWidget = new ComponentName(context, RecipeWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        onUpdate(context, appWidgetManager, appWidgetIds);
    }
}

