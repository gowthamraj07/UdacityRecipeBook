package com.asanam.udacityrecipebook.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asanam.udacityrecipebook.R;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        detailsFragmentView = inflater.inflate(R.layout.step_details_fragment_layout, container, false);

        exoPlayerFragment = getChildFragmentManager().findFragmentById(R.id.frag_exo_player);
        ivThumbnail = detailsFragmentView.findViewById(R.id.iv_thumbnail);

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
    public void showVideo(String url, String description) {
        bundle = new Bundle();
        bundle.putString("VIDEO_URL", url);

        Log.d(StepDetailsFragment.class.getSimpleName(), "VIDEO_URL : "+url);

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
    }

    @Override
    public void hideImage() {
        ivThumbnail.setVisibility(View.GONE);
    }

    @Override
    public void hideVideo() {
        exoPlayerFragment.getView().setVisibility(View.GONE);
        getChildFragmentManager().beginTransaction().remove(exoPlayerFragment).commit();
    }

    @Override
    public void hidePrevious() {

    }

    @Override
    public void showDescription(String description) {
        TextView tvDescription = detailsFragmentView.findViewById(R.id.tv_description);
        tvDescription.setText(description);
    }
}
