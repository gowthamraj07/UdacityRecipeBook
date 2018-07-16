package com.asanam.udacityrecipebook;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asanam.udacityrecipebook.fragments.ExoPlayerFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;

public class StepDetailsActivity extends AppCompatActivity implements ExoPlayerFragment.ExoPlayerListener, StepDetailsFragment.StepDetailListener {

    public static final String PLAYBACK_POSITION = "playbackPosition";
    public static final String CURRENT_WINDOW = "currentWindow";
    public static final String RECIPE_ID = "RECIPE_ID";
    public static final String STEP_ID = "STEP_ID";
    public static final String TAG = "StepDetailsActivity";
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
        Bundle stepDetails = getIntent().getBundleExtra("STEP_DETAILS");

        recipeId = stepDetails.getLong("RECIPE_ID");
        stepId = stepDetails.getLong("STEP_ID");

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
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        outState.putLong(RECIPE_ID, recipeId);
        outState.putLong(STEP_ID, stepId);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            recipeId = savedInstanceState.getLong(RECIPE_ID);
            stepId = savedInstanceState.getLong(STEP_ID);
            Log.d(TAG, "onRestoreInstanceState: Exoplayer [" + playbackPosition + "," + currentWindow + "]");
        }

    }

    @Override
    public void onSaveStepDetails(long recipeId, long stepId) {
        this.recipeId = recipeId;
        this.stepId = stepId;
    }
}
