package com.asanam.udacityrecipebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeDBHelper extends SQLiteOpenHelper {

    public RecipeDBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_RECIPE_TABLE = "CREATE TABLE "+DBContract.RecipeTable.TABLE_NAME+ " ( " +
                DBContract.RecipeTable._ID + " INTEGER PRIMARY KEY, " +
                DBContract.RecipeTable.COLUMN_RECIPE_ID + " INTEGER , " +
                DBContract.RecipeTable.COLUMN_NAME + " TEXT, " +
                DBContract.RecipeTable.COLUMN_SERVINGS + " TEXT, " +
                DBContract.RecipeTable.COLUMN_IMAGE + " TEXT" +
                ")";

        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE "+DBContract.IngredientsTable.TABLE_NAME+ " ( " +
                DBContract.IngredientsTable._ID + " INTEGER PRIMARY KEY, " +
                DBContract.IngredientsTable.COLUMN_INGREDIENT + " TEXT, " +
                DBContract.IngredientsTable.COLUMN_MEASURE + " TEXT, " +
                DBContract.IngredientsTable.COLUMN_QUANTITY + " TEXT, " +
                DBContract.IngredientsTable.COLUMN_RECIPE_ID + " INTEGER " +
                ")";

        String CREATE_STEP_TABLE = "CREATE TABLE "+DBContract.StepTable.TABLE_NAME+ " ( " +
                DBContract.StepTable._ID + " INTEGER PRIMARY KEY, " +
                DBContract.StepTable.COLUMN_DESCRIPTION + " TEXT, " +
                DBContract.StepTable.COLUMN_ID + " TEXT, " +
                DBContract.StepTable.COLUMN_RECIPE_ID + " INTEGER, " +
                DBContract.StepTable.COLUMN_SHORT_DESCRIPTION + " TEXT, " +
                DBContract.StepTable.COLUMN_THUMBNAIL_URL + " TEXT, " +
                DBContract.StepTable.COLUMN_VIDEO_URL + " TEXT " +
                ")";

        sqLiteDatabase.execSQL(CREATE_RECIPE_TABLE);
        sqLiteDatabase.execSQL(CREATE_INGREDIENTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_STEP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.StepTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.IngredientsTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.RecipeTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
