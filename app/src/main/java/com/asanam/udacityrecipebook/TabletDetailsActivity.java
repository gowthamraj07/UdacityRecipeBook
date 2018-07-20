package com.asanam.udacityrecipebook;

import android.content.pm.ActivityInfo;
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
    private Fragment stepDetailsFragment;

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
    }

    @Override
    public void onSelectStep(long recipeId, long stepId) {

        if(stepDetailsFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(stepDetailsFragment).commit();
        }

        stepDetailsFragment = new StepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.RECIPE_ID, recipeId);
        bundle.putLong(Constants.STEP_ID, stepId);
        stepDetailsFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_step_details_fragment_container, stepDetailsFragment)
                .commit();
    }

    @Override
    public void onSaveStepDetails(long recipeId, long stepId) {
        this.recipeId = recipeId;
    }

    @Override
    public void onReleaseExoPlayer(long playbackPosition, int currentWindow, boolean playWhenReady) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(stepDetailsFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(stepDetailsFragment).commit();
        }
    }
}
