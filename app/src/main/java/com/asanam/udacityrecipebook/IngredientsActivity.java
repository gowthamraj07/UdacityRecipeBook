package com.asanam.udacityrecipebook;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        RecyclerView rvIngredients = findViewById(R.id.rv_ingredients_list);
        Long recipeId = 1l;
        rvIngredients.setAdapter(new IngredientsAdapter(recipeId));
    }

    private class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {
        private Long recipeId;

        public IngredientsAdapter(Long recipeId) {
            this.recipeId = recipeId;
        }

        @NonNull
        @Override
        public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public IngredientsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
