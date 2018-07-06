package com.asanam.udacityrecipebook.db;

import android.provider.BaseColumns;

public class DBContract implements BaseColumns {

    public static final String DB_NAME = "recipe_db";
    public static final int VERSION = 1;

    public static class RecipeTable implements BaseColumns{
        public static final String TABLE_NAME = "recipe_table";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";
    }

    public static class IngredientsTable implements BaseColumns{
        public static final String TABLE_NAME = "ingredients_table";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
    }

    public static class StepTable implements BaseColumns{
        public static final String TABLE_NAME = "step_table";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SHORT_DESCRIPTION = "shortDescription";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_VIDEO_URL = "videoURL";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnailURL";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
    }
}
