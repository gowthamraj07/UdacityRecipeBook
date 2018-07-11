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
import android.widget.ImageView;
import android.widget.TextView;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.dto.Step;
import com.asanam.udacityrecipebook.presenter.StepDetailsPresenter;
import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.repository.RecipeDBRepository;
import com.asanam.udacityrecipebook.view.StepDetailsView;
import com.squareup.picasso.Picasso;

public class StepDetailsFragment extends Fragment implements StepDetailsView {

    private StepDetailsPresenter presenter;
    private Bundle bundle;
    private View detailsFragmentView;
    private Fragment exoPlayerFragment;
    private ImageView ivThumbnail;
    private Button btnPrevious;
    private Button btnNext;
    private long recipeId;
    private long stepId;

    private StepDetailListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        detailsFragmentView = inflater.inflate(R.layout.step_details_fragment_layout, container, false);

        DBRepository repository = new RecipeDBRepository(getContext());
        presenter = new StepDetailsPresenter(this, repository);

        return detailsFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        recipeId = getArguments().getLong("RECIPE_ID");
//        stepId = getArguments().getLong("STEP_ID");
//
//        Log.d(StepDetailsFragment.class.getSimpleName(), "Recipe id : "+ recipeId +", Step id : "+ stepId);
//
//        presenter.showStepDetailsScreen(recipeId, stepId);
    }

    public void showStepDetailsScreen(long recipeId, long stepId) {
        this.recipeId = recipeId;
        this.stepId = stepId;
        presenter.showStepDetailsScreen(recipeId, stepId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof StepDetailListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement "+StepDetailListener.class.getSimpleName());
        }

        listener = ((StepDetailListener)getActivity());
    }

    @Override
    public void onPause() {
        listener.onSaveStepDetails(recipeId, stepId);
        super.onPause();
    }

    @Override
    public void showVideo(String url, String description) {
        bundle = new Bundle();
        bundle.putString("VIDEO_URL", url);

        Log.d(StepDetailsFragment.class.getSimpleName(), "VIDEO_URL : "+url);

        Guideline guideLine = detailsFragmentView.findViewById(R.id.guideline2);
        guideLine.setGuidelinePercent(0.4f);

        if(exoPlayerFragment != null) {
            ((ExoPlayerFragment) exoPlayerFragment).showVideo(url);
        }

        showDescription(description);
    }

    @Override
    public void showImage(String imageUrl, String description) {
        showDescription(description);
        ivThumbnail.setVisibility(View.VISIBLE);
        Picasso.get().load(Uri.parse(imageUrl)).into(ivThumbnail);

        Guideline guideLine = detailsFragmentView.findViewById(R.id.guideline2);
        guideLine.setGuidelinePercent(0.4f);
    }

    @Override
    public void hideImage() {
        ivThumbnail.setVisibility(View.GONE);

        Guideline guideLine = detailsFragmentView.findViewById(R.id.guideline2);
        guideLine.setGuidelinePercent(0);
    }

    @Override
    public void hideVideo() {
        if(exoPlayerFragment != null && exoPlayerFragment.getView() != null) {
            exoPlayerFragment.getView().setVisibility(View.GONE);
            ((ExoPlayerFragment) exoPlayerFragment).releasePlayer();
        }

        Guideline guideLine = detailsFragmentView.findViewById(R.id.guideline2);
        guideLine.setGuidelinePercent(0);
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
    public void reset() {
        exoPlayerFragment = getChildFragmentManager().findFragmentById(R.id.frag_exo_player);
        if(exoPlayerFragment != null && exoPlayerFragment.getView() != null) {
            exoPlayerFragment.getView().setVisibility(View.VISIBLE);
        }
        ivThumbnail = detailsFragmentView.findViewById(R.id.iv_thumbnail);

        btnPrevious = detailsFragmentView.findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(new PreviousButtonListener());

        btnNext = detailsFragmentView.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new NextButtonListener());
    }

    @Override
    public void showDescription(String description) {
        TextView tvDescription = detailsFragmentView.findViewById(R.id.tv_description);
        tvDescription.setText(description);
    }

    private class PreviousButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            stepId--;
            ((ExoPlayerFragment) exoPlayerFragment).releasePlayer();
            presenter.showStepDetailsScreen(recipeId, stepId);
        }
    }

    private class NextButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            stepId++;
            ((ExoPlayerFragment) exoPlayerFragment).releasePlayer();
            presenter.showStepDetailsScreen(recipeId, stepId);
        }
    }

    public interface StepDetailListener {
        void onSaveStepDetails(long recipeId, long stepId);
    }
}
