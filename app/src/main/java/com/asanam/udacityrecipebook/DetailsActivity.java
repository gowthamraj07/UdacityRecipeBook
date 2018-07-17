package com.asanam.udacityrecipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.fragments.RecipeDetailsFragment;
import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;
import com.asanam.udacityrecipebook.provider.RecipeProvider;
import com.asanam.udacityrecipebook.utils.Constants;

public class DetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.StepSelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        long recipeId = getIntent().getLongExtra(Constants.RECIPE_ID, -1);
        Log.i(DetailsActivity.class.getSimpleName(), "recipeId : "+recipeId);

        Cursor query = getContentResolver().query(RecipeProvider.RECIPE_NAME_URI.buildUpon().appendPath("" + recipeId).build(),
                null, null, null, null);
        if(query != null && query.getCount() > 0) {
            query.moveToFirst();
            setTitle(query.getString(query.getColumnIndex(DBContract.RecipeTable.COLUMN_NAME)));
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_details);
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.RECIPE_ID, recipeId);
        fragment.setArguments(bundle);
    }

    @Override
    public void onSelectStep(long recipeId, long id) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.RECIPE_ID, recipeId);
        bundle.putLong(Constants.STEP_ID, id);
        intent.putExtra(Constants.STEP_DETAILS, bundle);
        startActivity(intent);
    }
}
