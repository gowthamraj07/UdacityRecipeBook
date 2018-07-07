package com.asanam.udacityrecipebook;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.asanam.udacityrecipebook.fragments.StepDetailsFragment;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Fragment stepDetailsFragment = getSupportFragmentManager().findFragmentById(R.id.frag_step_details_fragment);
        Bundle stepDetails = getIntent().getBundleExtra("STEP_DETAILS");

        stepDetailsFragment.setArguments(stepDetails);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
