package com.asanam.udacityrecipebook.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asanam.udacityrecipebook.IngredientsActivity;
import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.adapter.StepsAdapter;
import com.asanam.udacityrecipebook.dto.Step;
import com.asanam.udacityrecipebook.presenter.DetailsPresenter;
import com.asanam.udacityrecipebook.repository.DBRepository;
import com.asanam.udacityrecipebook.repository.RecipeDBRepository;
import com.asanam.udacityrecipebook.utils.Constants;
import com.asanam.udacityrecipebook.view.DetailsView;

public class RecipeDetailsFragment extends Fragment implements DetailsView {

    private DetailsPresenter presenter;
    private RecyclerView rvSteps;
    private StepSelectionListener listener;
    private long recipeId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View detailsView = inflater.inflate(R.layout.recipe_details_fragment_layout, container, false);

        rvSteps = detailsView.findViewById(R.id.rv_steps);
        View ingredients = detailsView.findViewById(R.id.inc_ingredients);
        ingredients.setOnClickListener(new IngredientsClickListener());

        if(getActivity() instanceof StepSelectionListener) {
            listener = (StepSelectionListener) getActivity();
        } else {
            throw new ClassCastException(getActivity().toString()
                    + getString(R.string.must_implement_step_selection_listener));
        }

        DBRepository repository = new RecipeDBRepository(getContext());
        presenter = new DetailsPresenter(this, repository);
        return detailsView;
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeId = getArguments().getLong(Constants.RECIPE_ID);
        Log.i(RecipeDetailsFragment.class.getSimpleName(), "recipeId : "+ recipeId);
        presenter.getSteps(recipeId);
    }

    @Override
    public void showDetails(Cursor cursor) {
        StepsAdapter adapter = new StepsAdapter(cursor, getContext(), listener);
        rvSteps.setAdapter(adapter);
    }

    public interface StepSelectionListener {
        void onSelectStep(long recipeId, long id);
    }

    private class IngredientsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), IngredientsActivity.class);
            intent.putExtra(Constants.RECIPE_ID, recipeId);
            getContext().startActivity(intent);
        }
    }
}
