package com.asanam.udacityrecipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.asanam.udacityrecipebook.view.AndroidRecipeCardsView;
import com.asanam.udacityrecipebook.view.RecipeCardsView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvRecipeCard = findViewById(R.id.rv_recipe_card);
        RecipeCardsView view = new AndroidRecipeCardsView(getApplicationContext(), rvRecipeCard);
    }
}
