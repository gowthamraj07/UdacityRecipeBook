package com.asanam.udacityrecipebook;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.asanam.udacityrecipebook.network.HttpNetworkApi;
import com.asanam.udacityrecipebook.network.NetworkApi;
import com.asanam.udacityrecipebook.presenter.RecipeCardsPresenter;
import com.asanam.udacityrecipebook.repository.NetworkRecipeRepository;
import com.asanam.udacityrecipebook.repository.RecipeDBRepository;
import com.asanam.udacityrecipebook.repository.RecipeRepository;
import com.asanam.udacityrecipebook.view.AndroidRecipeCardsView;
import com.asanam.udacityrecipebook.view.RecipeCardsView;

public class MainActivity extends AppCompatActivity {

    CountingIdlingResource countingIdlingResource = new CountingIdlingResource("DOWNLOAD");

    public static final String TAG = "MainActivity";
    private RecipeCardsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvRecipeCard = findViewById(R.id.rv_recipe_card);
        RecipeCardsView view = new AndroidRecipeCardsView(getApplicationContext(), rvRecipeCard);
        if(getResources().getBoolean(R.bool.is_tablet)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            rvRecipeCard.setLayoutManager(new GridLayoutManager(this, 4));
        }

        NetworkApi api = new HttpNetworkApi();
        RecipeRepository repository = new NetworkRecipeRepository(api, getContentResolver(), new NetworkListener());
        presenter = new RecipeCardsPresenter(view, repository);

        countingIdlingResource.increment();
        presenter.showCards();
    }

    private void showRecipeActivity(long recipeId) {
        Intent intent = null;
        if (getResources().getBoolean(R.bool.is_tablet)) {
            intent = new Intent(this, TabletDetailsActivity.class);
        } else {
            intent = new Intent(this, DetailsActivity.class);
        }
        intent.putExtra("RECIPE_ID", recipeId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return countingIdlingResource;
    }

    private class NetworkListener implements NetworkRecipeRepository.NetworkRepositoryCallback {
        @Override
        public void onSuccess() {
            if(getIntent() != null) {
                long recipeId = getIntent().getLongExtra("RECIPE_ID", -1);
                Log.d(TAG, "onCreate: recipeId : "+recipeId);
                if(recipeId != -1) {
                    showRecipeActivity(recipeId);
                }
            }
            countingIdlingResource.decrement();
        }
    }
}
