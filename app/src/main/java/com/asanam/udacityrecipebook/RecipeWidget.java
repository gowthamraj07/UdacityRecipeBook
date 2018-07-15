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

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public static final String TAG = "RecipeWidget";
    public static final String RECIPE_ACTION = "RECIPE_ACTION";
    public static final String NEXT = "NEXT";
    public static final String PREVIOUS = "PREVIOUS";

    private static Long recipeId = 0l;
    private static CharSequence widgetText;
    private RemoteViews views;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.d(TAG, "updateAppWidget: ");

        if(!setWidgetValues(context, recipeId)) {
            return;
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.tv_recipe_title, pendingIntent);

        int[] ids = new int[1];
        ids[0] = appWidgetId;

        Intent nextIntent = new Intent(context, getClass());
        nextIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        nextIntent.putExtra(RECIPE_ACTION, NEXT);
        PendingIntent pendingNextIntent = PendingIntent.getBroadcast(context, 1235, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_next, pendingNextIntent);

        Intent previousIntent = new Intent(context, getClass());
        previousIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        previousIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        previousIntent.putExtra(RECIPE_ACTION, PREVIOUS);
        PendingIntent pendingPreviousIntent = PendingIntent.getBroadcast(context, 1236, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_previous, pendingPreviousIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private boolean setWidgetValues(Context context, Long recipeId) {

        Log.d(TAG, "setWidgetValues: recipeId = "+recipeId);

        Cursor cursor = context.getContentResolver().query(RecipeProvider.RECIPE_NAME_URI, null, null, null, null);

        if(recipeId != null) {
            while(cursor.moveToNext()) {
                long cursorRecipeId = cursor.getLong(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_RECIPE_ID));
                if(cursorRecipeId > recipeId) {
                    break;
                }
            }
        } else {
            cursor.moveToFirst();
        }

        if(cursor.isAfterLast()) {
            return false;
        }

        widgetText = context.getString(R.string.appwidget_text);

        if(cursor != null && cursor.getCount()>0) {
            long aLong = cursor.getLong(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_RECIPE_ID));
            widgetText = cursor.getString(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_NAME));

            //context.getContentResolver().query(RecipeProvider.INGREDIENTS_URI.buildUpon().appendPath(aLong).build(), null, null, null, null);
        }

        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.tv_recipe_title, widgetText);

        return true;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: recipeId : "+recipeId);

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
        Log.d(TAG, "onReceive: recipeId : "+recipeId);
        Log.d(TAG, "onReceive: stringExtra : "+stringExtra);
        if(stringExtra == null || NEXT.equals(stringExtra)) {
            ++recipeId;
        } else {
            recipeId = recipeId == 0 ? 0 : --recipeId;
        }

        Log.d(TAG, "onReceive: recipeId : "+recipeId);

        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            if(views == null) {
                return;
            }

            views.setTextViewText(R.id.tv_recipe_title, widgetText);
            ComponentName thisWidget = new ComponentName( context, RecipeWidget.class );
            AppWidgetManager.getInstance( context ).updateAppWidget( thisWidget, views );
        }
    }



}

