package com.asanam.udacityrecipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.asanam.udacityrecipebook.network.HttpNetworkApi;
import com.asanam.udacityrecipebook.network.NetworkApi;
import com.asanam.udacityrecipebook.presenter.RecipeCardsPresenter;
import com.asanam.udacityrecipebook.repository.NetworkRecipeRepository;
import com.asanam.udacityrecipebook.repository.RecipeDBRepository;
import com.asanam.udacityrecipebook.repository.RecipeRepository;
import com.asanam.udacityrecipebook.view.AndroidRecipeCardsView;
import com.asanam.udacityrecipebook.view.RecipeCardsView;

public class MainActivity extends AppCompatActivity {

    private RecipeCardsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvRecipeCard = findViewById(R.id.rv_recipe_card);
        RecipeCardsView view = new AndroidRecipeCardsView(getApplicationContext(), rvRecipeCard);

        NetworkApi api = new HttpNetworkApi();
        RecipeRepository repository = new NetworkRecipeRepository(api, getContentResolver());
        presenter = new RecipeCardsPresenter(view, repository);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.showCards();
    }
}
