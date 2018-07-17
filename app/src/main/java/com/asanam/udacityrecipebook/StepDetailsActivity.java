package com.asanam.udacityrecipebook;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asanam.udacityrecipebook.fragments.ExoPlayerFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;
import com.asanam.udacityrecipebook.utils.Constants;

public class StepDetailsActivity extends AppCompatActivity implements ExoPlayerFragment.ExoPlayerListener, StepDetailsFragment.StepDetailListener {

    public static final String TAG = StepDetailsActivity.class.getSimpleName();
    private Fragment stepDetailsFragment;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private long recipeId;
    private long stepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        stepDetailsFragment = getSupportFragmentManager().findFragmentById(R.id.frag_step_details_fragment);
        Bundle stepDetails = getIntent().getBundleExtra(Constants.STEP_DETAILS);

        recipeId = stepDetails.getLong(Constants.RECIPE_ID);
        stepId = stepDetails.getLong(Constants.STEP_ID);

//        stepDetailsFragment.setArguments(stepDetails);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        ((StepDetailsFragment) stepDetailsFragment).showStepDetailsScreen(recipeId, stepId);

        Fragment exoPlayer = stepDetailsFragment.getChildFragmentManager().findFragmentById(R.id.frag_exo_player);
        if (exoPlayer != null) {
            ((ExoPlayerFragment) exoPlayer).seekTo(playbackPosition, currentWindow);
        }
    }

    @Override
    public void onReleaseExoPlayer(long playbackPosition, int currentWindow) {
        Log.d(TAG, "onReleaseExoPlayer: Exoplayer [" + playbackPosition + "," + currentWindow + "]");
        this.playbackPosition = playbackPosition;
        this.currentWindow = currentWindow;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        Log.d(TAG, "onSaveInstanceState: Exoplayer [" + playbackPosition + "," + currentWindow + "]");
        outState.putLong(Constants.PLAYBACK_POSITION, playbackPosition);
        outState.putInt(Constants.CURRENT_WINDOW, currentWindow);
        outState.putLong(Constants.RECIPE_ID, recipeId);
        outState.putLong(Constants.STEP_ID, stepId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(Constants.PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(Constants.CURRENT_WINDOW);
            recipeId = savedInstanceState.getLong(Constants.RECIPE_ID);
            stepId = savedInstanceState.getLong(Constants.STEP_ID);
            Log.d(TAG, "onRestoreInstanceState: Exoplayer [" + playbackPosition + "," + currentWindow + "]");
        }

    }

    @Override
    public void onSaveStepDetails(long recipeId, long stepId) {
        this.recipeId = recipeId;
        this.stepId = stepId;
    }
}
