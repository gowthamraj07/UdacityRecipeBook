package com.asanam.udacityrecipebook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.asanam.udacityrecipebook.fragments.ExoPlayerFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;
import com.asanam.udacityrecipebook.utils.Constants;

public class StepDetailsActivity extends AppCompatActivity implements ExoPlayerFragment.ExoPlayerListener, StepDetailsFragment.StepDetailListener {

    public static final String TAG = StepDetailsActivity.class.getSimpleName();
    private Fragment stepDetailsFragment;
    private long recipeId;
    private long stepId;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Bundle stepDetails = getIntent().getBundleExtra(Constants.STEP_DETAILS);

        recipeId = stepDetails.getLong(Constants.RECIPE_ID);
        stepId = stepDetails.getLong(Constants.STEP_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        loadStepDetailsFragment();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
        
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");

    }

    @Override
    public void onReleaseExoPlayer(long playbackPosition, int currentWindow, boolean playWhenReady) {
        this.playbackPosition = playbackPosition;
        this.currentWindow = currentWindow;
        this.playWhenReady = playWhenReady;
        Log.d(TAG, "onReleaseExoPlayer: ");
    }

    @Override
    public void onSaveStepDetails(long recipeId, long stepId) {
        this.recipeId = recipeId;
        this.stepId = stepId;
    }

    @Override
    public void onTraverseButtonListener(long recipeId, long newStepId) {
        Log.d(TAG, "onTraverseButtonListener: ");
        this.recipeId = recipeId;
        this.stepId = newStepId;
        this.playWhenReady = true;
        this.playbackPosition = 0;
        this.currentWindow = 0;

        loadStepDetailsFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(Constants.RECIPE_ID, recipeId);
        outState.putLong(Constants.STEP_ID, stepId);
        outState.putLong(Constants.PLAYBACK_POSITION, playbackPosition);
        outState.putInt(Constants.CURRENT_WINDOW, currentWindow);
        outState.putBoolean(Constants.PLAYER_WHEN_READY, playWhenReady);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        stepId = savedInstanceState.getLong(Constants.STEP_ID);
        recipeId = savedInstanceState.getLong(Constants.RECIPE_ID);
        playbackPosition = savedInstanceState.getLong(Constants.PLAYBACK_POSITION);
        currentWindow = savedInstanceState.getInt(Constants.CURRENT_WINDOW);
        playWhenReady = savedInstanceState.getBoolean(Constants.PLAYER_WHEN_READY, true);
    }

    private void loadStepDetailsFragment() {

        stepDetailsFragment = getSupportFragmentManager().findFragmentByTag(Constants.STEP_DETAILS_FRAG_TAG);
        if(stepDetailsFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(stepDetailsFragment)
                    .detach(stepDetailsFragment)
                    .commit();
        }

        FrameLayout frameLayout = findViewById(R.id.frag_step_details_fragment_container);
        frameLayout.removeAllViews();

        Bundle stepDetails = new Bundle();
        stepDetails.putLong(Constants.RECIPE_ID, recipeId);
        stepDetails.putLong(Constants.STEP_ID, stepId);
        stepDetails.putLong(Constants.PLAYBACK_POSITION, playbackPosition);
        stepDetails.putInt(Constants.CURRENT_WINDOW, currentWindow);
        stepDetails.putBoolean(Constants.PLAYER_WHEN_READY, playWhenReady);

        stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setArguments(stepDetails);
        getSupportFragmentManager().beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.frag_step_details_fragment_container, stepDetailsFragment, Constants.STEP_DETAILS_FRAG_TAG)
                .commit();
    }

}
