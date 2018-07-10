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

        view.reset();

        Cursor stepDetails = repository.getStepDetails(stepId, recipeId);
        stepDetails.moveToFirst();
        String videoUrl = stepDetails.getString(stepDetails.getColumnIndex(DBContract.StepTable.COLUMN_VIDEO_URL));
        String description = stepDetails.getString(stepDetails.getColumnIndex(DBContract.StepTable.COLUMN_DESCRIPTION));
        String thumbnailUrl = stepDetails.getString(stepDetails.getColumnIndex(DBContract.StepTable.COLUMN_THUMBNAIL_URL));

        Cursor previous = repository.getPreviousStepDetails(recipeId, stepId);
        if(previous!= null && previous.getCount() > 0) {
            view.showPrevious();
        } else {
            view.hidePrevious();
        }

        Cursor next = repository.getNextStepDetails(recipeId, stepId);
        if(next != null && next.getCount() > 0) {
            view.showNext();
        } else {
            view.hideNext();
        }

        if(videoUrl != null && !videoUrl.isEmpty()) {
            showVideo(videoUrl, description);
            return;
        }


        if(thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
            if(thumbnailUrl.endsWith(".mp4")) {
                showVideo(thumbnailUrl, description);
            } else {
                showImage(description, thumbnailUrl);
            }
            return;
        }

        view.hideImage();
        view.hideVideo();

        if(description != null && !description.isEmpty()) {
            view.showDescription(description);
        }
    }

    private void showImage(String description, String thumbnailUrl) {
        view.showImage(thumbnailUrl, description);
        view.hideVideo();
    }

    private void showVideo(String videoUrl, String description) {
        view.showVideo(videoUrl, description);
        view.hideImage();
    }
}
