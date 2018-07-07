package com.asanam.udacityrecipebook.domain;

import android.content.ContentValues;
import android.util.Log;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.dto.Step;

public class StepDomain {
    private Step step;

    public StepDomain(Step step) {
        this.step = step;
    }

    public ContentValues getContentValues(Integer recipeId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.StepTable.COLUMN_DESCRIPTION, step.getDescription());
        contentValues.put(DBContract.StepTable.COLUMN_ID, step.getId());
        contentValues.put(DBContract.StepTable.COLUMN_RECIPE_ID, recipeId);
        contentValues.put(DBContract.StepTable.COLUMN_SHORT_DESCRIPTION, step.getShortDescription());
        contentValues.put(DBContract.StepTable.COLUMN_THUMBNAIL_URL, step.getThumbnailURL());
        contentValues.put(DBContract.StepTable.COLUMN_VIDEO_URL, step.getVideoURL());

        Log.d(StepDomain.class.getSimpleName(), "Des : "+step.getShortDescription()+", URL "+step.getVideoURL());

        return contentValues;
    }
}
