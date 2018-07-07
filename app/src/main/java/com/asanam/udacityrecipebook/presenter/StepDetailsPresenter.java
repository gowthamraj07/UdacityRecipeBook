package com.asanam.udacityrecipebook.presenter;

import android.database.Cursor;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.view.StepDetailsView;

public class StepDetailsPresenter {
    private StepDetailsView view;
    private DBRepository repository;

    public StepDetailsPresenter(StepDetailsView view, DBRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void showStepDetailsScreen(Long recipeId, Long stepId) {
        Cursor stepDetails = repository.getStepDetails(stepId, recipeId);
        stepDetails.moveToFirst();
        String videoUrl = stepDetails.getString(stepDetails.getColumnIndex(DBContract.StepTable.COLUMN_VIDEO_URL));
        String description = stepDetails.getString(stepDetails.getColumnIndex(DBContract.StepTable.COLUMN_DESCRIPTION));

        view.showVideo(videoUrl, description);
    }
}
