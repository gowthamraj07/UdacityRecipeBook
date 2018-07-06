package com.asanam.udacityrecipebook;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DetailsActivity extends AppCompatActivity {

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
}
