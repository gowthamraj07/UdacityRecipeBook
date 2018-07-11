package com.asanam.udacityrecipebook;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asanam.udacityrecipebook.fragments.ExoPlayerFragment;
import com.asanam.udacityrecipebook.fragments.RecipeDetailsFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;

public class TabletDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.StepSelectionListener, StepDetailsFragment.StepDetailListener, ExoPlayerFragment.ExoPlayerListener {

    private long recipeId;
    private long stepId;
    private long playbackPosition;
    private int currentWindow;
    private Fragment exoPlayer;
    private Fragment stepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet_details);

        recipeId = getIntent().getLongExtra("RECIPE_ID", -1);

        Fragment detailsFragment = getSupportFragmentManager().findFragmentById(R.id.frag_details);

        Bundle bundle = new Bundle();
        bundle.putLong("RECIPE_ID", recipeId);
        detailsFragment.setArguments(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSelectStep(long recipeId, long stepId) {
        if (stepDetailsFragment == null) {
            stepDetailsFragment = getSupportFragmentManager().findFragmentById(R.id.frag_step_details_fragment);
            exoPlayer = stepDetailsFragment.getChildFragmentManager().findFragmentById(R.id.frag_exo_player);
        }

        if (exoPlayer != null) {
            ((ExoPlayerFragment) exoPlayer).releasePlayer();
        }

        ((StepDetailsFragment) stepDetailsFragment).showStepDetailsScreen(recipeId, stepId);
    }

    @Override
    public void onSaveStepDetails(long recipeId, long stepId) {
        this.recipeId = recipeId;
        this.stepId = stepId;
    }

    @Override
    public void onReleaseExoPlayer(long playbackPosition, int currentWindow) {
        this.playbackPosition = playbackPosition;
        this.currentWindow = currentWindow;
    }
}
