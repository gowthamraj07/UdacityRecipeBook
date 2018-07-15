package com.asanam.udacityrecipebook.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.asanam.udacityrecipebook.db.RecipeDBManager;

public class RecipeProvider extends ContentProvider {

    private static final String AUTHORITY = "com.asanam.udacityrecipebook";
    private static final String RECIPE_BASE_PATH = "recipe";
    private static final String INGREDIENT_BASE_PATH = "ingredient";
    private static final String STEPS_BASE_PATH = "steps";
    private static final String STEP_DETAILS_BASE_PATH = "step_details";

    public static final String BASE_URI_CONTENT = "content://" + AUTHORITY + "/";

    public static final Uri RECIPE_NAME_URI = Uri.parse(BASE_URI_CONTENT + RECIPE_BASE_PATH);
    public static final Uri STEP_URI = Uri.parse(BASE_URI_CONTENT + STEPS_BASE_PATH);
    public static final Uri STEP_DETAILS_URI = Uri.parse(BASE_URI_CONTENT + STEP_DETAILS_BASE_PATH);
    public static final Uri INGREDIENTS_URI = Uri.parse(BASE_URI_CONTENT + INGREDIENT_BASE_PATH);

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, RECIPE_BASE_PATH + "", 1);
        uriMatcher.addURI(AUTHORITY, RECIPE_BASE_PATH + "/#", 2);
        uriMatcher.addURI(AUTHORITY, INGREDIENT_BASE_PATH + "", 10);
        uriMatcher.addURI(AUTHORITY, INGREDIENT_BASE_PATH + "/#", 11);
        uriMatcher.addURI(AUTHORITY, STEPS_BASE_PATH + "", 20);
        uriMatcher.addURI(AUTHORITY, STEPS_BASE_PATH + "/#", 21);
        uriMatcher.addURI(AUTHORITY, STEP_DETAILS_BASE_PATH + "/#", 22);
    }

    private RecipeDBManager dbManager;

    @Override
    public boolean onCreate() {
        dbManager = new RecipeDBManager(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return dbManager.queryAllRecipeNames();
            case 10:
                return dbManager.queryAllIngredients();
            case 11: {
                long recipeId = Long.parseLong(uri.getLastPathSegment());
                return dbManager.queryAllIngredientsFor(recipeId);
            }
            case 21: {
                Integer recipeId = Integer.parseInt(uri.getLastPathSegment());
                return dbManager.getSteps(recipeId);
            }
            case 22: {
                Integer recipeId = Integer.parseInt(uri.getLastPathSegment());
                return dbManager.getStepDetails(recipeId, s);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (uriMatcher.match(uri)) {
            case 1:
                dbManager.addRecipes(values);
                break;
            case 11: {
                Integer recipeId = Integer.parseInt(uri.getLastPathSegment());
                dbManager.addIngredients(values, recipeId);
            }
            break;

            case 21: {
                Integer recipeId = Integer.parseInt(uri.getLastPathSegment());
                dbManager.addSteps(values, recipeId);
            }
            break;
        }
        return super.bulkInsert(uri, values);
    }
}
