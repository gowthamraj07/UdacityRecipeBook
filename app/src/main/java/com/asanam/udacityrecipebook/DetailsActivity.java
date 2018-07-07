package com.asanam.udacityrecipebook;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asanam.udacityrecipebook.fragments.RecipeDetailsFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;

public class DetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.StepSelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        long recipeId = getIntent().getLongExtra("RECIPE_ID", -1);
        Log.i(DetailsActivity.class.getSimpleName(), "recipeId : "+recipeId);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_details);
        Bundle bundle = new Bundle();
        bundle.putLong("RECIPE_ID", recipeId);
        fragment.setArguments(bundle);
    }

    @Override
    public void onSelectStep(long recipeId, long id) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("RECIPE_ID", recipeId);
        bundle.putLong("STEP_ID", id);
        intent.putExtra("STEP_DETAILS", bundle);
        startActivity(intent);
    }
}
