package com.asanam.udacityrecipebook.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.presenter.StepDetailsPresenter;
import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.repository.RecipeDBRepository;
import com.asanam.udacityrecipebook.utils.Constants;
import com.asanam.udacityrecipebook.view.StepDetailsView;
import com.squareup.picasso.Picasso;

public class StepDetailsFragment extends Fragment implements StepDetailsView {

    private StepDetailsPresenter presenter;
    private View detailsFragmentView;
    private Fragment exoPlayerFragment;
    private ImageView ivThumbnail;
    private Button btnPrevious;
    private Button btnNext;
    private long recipeId;
    private long stepId;

    private StepDetailListener listener;
    private FrameLayout exoPlayerContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        detailsFragmentView = inflater.inflate(R.layout.step_details_fragment_layout, container, false);

        DBRepository repository = new RecipeDBRepository(getContext());
        presenter = new StepDetailsPresenter(this, repository);
        exoPlayerFragment = new ExoPlayerFragment();

        return detailsFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getArguments() == null) {
            return;
        }

        recipeId = getArguments().getLong("RECIPE_ID");
        stepId = getArguments().getLong("STEP_ID");

        presenter.showStepDetailsScreen(recipeId, stepId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity()!=null && !(getActivity() instanceof StepDetailListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement "+StepDetailListener.class.getSimpleName());
        }

        listener = ((StepDetailListener)getActivity());
    }

    @Override
    public void onPause() {
        listener.onSaveStepDetails(recipeId, stepId);
        super.onPause();
        releasePlayer();
    }

    @Override
    public void showVideo(String url, String description) {
        Log.d(StepDetailsFragment.class.getSimpleName(), "VIDEO_URL : "+url);

        showGuideLine();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.VIDEO_URL, url);

        Bundle arguments = getArguments();
        if(arguments != null) {
            bundle.putLong(Constants.PLAYBACK_POSITION, arguments.getLong(Constants.PLAYBACK_POSITION, 0));
            bundle.putInt(Constants.CURRENT_WINDOW, arguments.getInt(Constants.CURRENT_WINDOW, 0));
            boolean playWhenReady = arguments.getBoolean(Constants.PLAYER_WHEN_READY, true);
            bundle.putBoolean(Constants.PLAYER_WHEN_READY, playWhenReady);
        }

        if(exoPlayerFragment != null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .remove(exoPlayerFragment)
                    .commit();
        } else {
            exoPlayerFragment = new ExoPlayerFragment();
        }

        exoPlayerFragment.setArguments(bundle);
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.frag_exo_player_container, exoPlayerFragment)
                .commit();

        showDescription(description);
    }

    @Override
    public void showImage(String imageUrl, String description) {
        showDescription(description);
        ivThumbnail.setVisibility(View.VISIBLE);
        Picasso.get().load(Uri.parse(imageUrl)).into(ivThumbnail);

        showGuideLine();
    }

    private void showGuideLine() {
        Guideline guideLine = detailsFragmentView.findViewById(R.id.guideline2);
        if(guideLine != null) {
            guideLine.setGuidelinePercent(0.4f);
        }
    }

    @Override
    public void hideImage() {
        ivThumbnail.setVisibility(View.GONE);
    }

    @Override
    public void hideVideo() {
        releasePlayer();
        exoPlayerContainer.removeAllViews();
    }

    @Override
    public void hidePrevious() {
        btnPrevious.setVisibility(View.GONE);
    }

    @Override
    public void showPrevious() {
        btnPrevious.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNext() {
        btnNext.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNext() {
        btnNext.setVisibility(View.GONE);
    }

    @Override
    public void initializeViews() {
        exoPlayerContainer = detailsFragmentView.findViewById(R.id.frag_exo_player_container);
        exoPlayerContainer.removeAllViews();

        ivThumbnail = detailsFragmentView.findViewById(R.id.iv_thumbnail);
        btnPrevious = detailsFragmentView.findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(new PreviousButtonListener());

        btnNext = detailsFragmentView.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new NextButtonListener());
    }

    @Override
    public void hideImageAndVideo() {
        Guideline guideLine = detailsFragmentView.findViewById(R.id.guideline2);
        if(guideLine != null) {
            guideLine.setGuidelinePercent(0);
        }
    }

    @Override
    public void showDescription(String description) {
        TextView tvDescription = detailsFragmentView.findViewById(R.id.tv_description);
        tvDescription.setText(description);
    }

    public interface StepDetailListener {
        void onSaveStepDetails(long recipeId, long stepId);
    }

    private class PreviousButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            stepId--;
            releasePlayer();
            presenter.showStepDetailsScreen(recipeId, stepId);
        }
    }

    private class NextButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            stepId++;
            releasePlayer();
            presenter.showStepDetailsScreen(recipeId, stepId);
        }
    }

    private void releasePlayer() {
        if(exoPlayerFragment != null) {
            getChildFragmentManager().beginTransaction()
                    .remove(exoPlayerFragment)
                    .commit();
        }

        if(exoPlayerContainer != null) {
            exoPlayerContainer.removeAllViews();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(Constants.RECIPE_ID, recipeId);
        outState.putLong(Constants.STEP_ID, stepId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            recipeId = savedInstanceState.getLong(Constants.RECIPE_ID);
            stepId = savedInstanceState.getLong(Constants.STEP_ID);
        }
    }
}
