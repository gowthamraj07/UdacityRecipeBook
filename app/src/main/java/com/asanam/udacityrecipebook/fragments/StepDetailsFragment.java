package com.asanam.udacityrecipebook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.domain.StepDomain;
import com.asanam.udacityrecipebook.presenter.StepDetailsPresenter;
import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.repository.RecipeDBRepository;
import com.asanam.udacityrecipebook.view.StepDetailsView;

public class StepDetailsFragment extends Fragment implements StepDetailsView {

    private StepDetailsPresenter presenter;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View detailsFragmentView = inflater.inflate(R.layout.step_details_fragment_layout, container, false);

        DBRepository repository = new RecipeDBRepository(getContext());
        presenter = new StepDetailsPresenter(this, repository);

        return detailsFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        long recipeId = getArguments().getLong("RECIPE_ID");
        long stepId = getArguments().getLong("STEP_ID");

        Log.d(StepDetailsFragment.class.getSimpleName(), "Recipe id : "+recipeId+", Step id : "+stepId);

        presenter.showStepDetailsScreen(recipeId, stepId);
    }

    @Override
    public void showVideo(String url) {
        bundle = new Bundle();
        bundle.putString("VIDEO_URL", url);

        Log.d(StepDetailsFragment.class.getSimpleName(), "VIDEO_URL : "+url);

        Fragment exoPlayerFragment = getChildFragmentManager().findFragmentById(R.id.frag_exo_player);
        if(exoPlayerFragment != null) {
            ((ExoPlayerFragment)exoPlayerFragment).showVideo(url);
        }
    }
}
