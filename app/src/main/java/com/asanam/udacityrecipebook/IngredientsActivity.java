package com.asanam.udacityrecipebook;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.provider.RecipeProvider;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        RecyclerView rvIngredients = findViewById(R.id.rv_ingredients_list);
        Long recipeId = getIntent().getLongExtra("RECIPE_ID", 0l);
        rvIngredients.setAdapter(new IngredientsAdapter(recipeId));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

        private final Cursor query;

        public IngredientsAdapter(Long recipeId) {
            query = getContentResolver().query(RecipeProvider.INGREDIENTS_URI.buildUpon().appendPath(recipeId.toString()).build(),
                    null, null, null, null);
        }

        @NonNull
        @Override
        public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View tvIngredientItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item_layout, null);
            return new IngredientsViewHolder(tvIngredientItem);
        }

        @Override
        public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
            query.moveToPosition(position);
            holder.bind(query);
        }

        @Override
        public int getItemCount() {
            return query.getCount();
        }
    }

    private class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView itemView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            this.itemView = (TextView) itemView.findViewById(R.id.tv_ingredient_item);
        }

        public void bind(Cursor query) {
            itemView.setText(query.getString(query.getColumnIndex(DBContract.IngredientsTable.COLUMN_INGREDIENT)));
        }
    }
}
