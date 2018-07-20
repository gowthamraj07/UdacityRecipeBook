package com.asanam.udacityrecipebook;

import android.content.pm.ActivityInfo;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.asanam.udacityrecipebook.fragments.ExoPlayerFragment;
import com.asanam.udacityrecipebook.fragments.RecipeDetailsFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;
import com.asanam.udacityrecipebook.utils.Constants;

public class TabletDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.StepSelectionListener, StepDetailsFragment.StepDetailListener, ExoPlayerFragment.ExoPlayerListener {

    private long recipeId;
    private long stepId;
    private Fragment stepDetailsFragment;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private boolean isNothingSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tablet_details);

        recipeId = getIntent().getLongExtra(Constants.RECIPE_ID, -1);

        Fragment detailsFragment = getSupportFragmentManager().findFragmentById(R.id.frag_details);

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.RECIPE_ID, recipeId);
        detailsFragment.setArguments(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FrameLayout frameLayout = findViewById(R.id.frag_step_details_fragment_container);
        frameLayout.removeAllViews();

        if(!isNothingSelected) {
            onSelectStep(recipeId, stepId);
        }
    }

    @Override
    public void onSelectStep(long recipeId, long stepId) {
        this.recipeId = recipeId;
        this.stepId = stepId;
        this.isNothingSelected = false;

        stepDetailsFragment = getSupportFragmentManager().findFragmentByTag(Constants.STEP_DETAILS_FRAG_TAG);
        if(stepDetailsFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(stepDetailsFragment)
                    .detach(stepDetailsFragment)
                    .commit();
        }

        stepDetailsFragment = new StepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.RECIPE_ID, recipeId);
        bundle.putLong(Constants.STEP_ID, stepId);
        bundle.putLong(Constants.PLAYBACK_POSITION, playbackPosition);
        bundle.putInt(Constants.CURRENT_WINDOW, currentWindow);
        bundle.putBoolean(Constants.PLAYER_WHEN_READY, playWhenReady);

        stepDetailsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_step_details_fragment_container, stepDetailsFragment, Constants.STEP_DETAILS_FRAG_TAG)
                .commit();
    }

    @Override
    public void onSaveStepDetails(long recipeId, long stepId) {
        this.recipeId = recipeId;
        this.stepId = stepId;
    }

    @Override
    public void onTraverseButtonListener(long recipeId, long newStepId) {
        this.playbackPosition = 0;
        this.currentWindow = 0;
        this.playWhenReady = true;
        onSelectStep(recipeId, newStepId);
    }

    @Override
    public void onReleaseExoPlayer(long playbackPosition, int currentWindow, boolean playWhenReady) {
        this.playbackPosition = playbackPosition;
        this.currentWindow = currentWindow;
        this.playWhenReady = playWhenReady;
    }

    @Override
    protected void onPause() {
        super.onPause();
        stepDetailsFragment = getSupportFragmentManager().findFragmentByTag(Constants.STEP_DETAILS_FRAG_TAG);
        if(stepDetailsFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(stepDetailsFragment)
                    .detach(stepDetailsFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(Constants.RECIPE_ID, recipeId);
        outState.putLong(Constants.STEP_ID, stepId);
        outState.putLong(Constants.PLAYBACK_POSITION, playbackPosition);
        outState.putInt(Constants.CURRENT_WINDOW, currentWindow);
        outState.putBoolean(Constants.PLAYER_WHEN_READY, playWhenReady);
        outState.putBoolean(Constants.IS_NOTHING_SELECTED, isNothingSelected);

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
        isNothingSelected = savedInstanceState.getBoolean(Constants.IS_NOTHING_SELECTED, true);
    }
}
