package com.asanam.udacityrecipebook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.domain.Recipe;
import com.asanam.udacityrecipebook.holder.RecipeViewHolder;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context applicationContext;
    private Cursor cursor;

    public RecipeAdapter(Context applicationContext, Cursor cursor) {
        this.applicationContext = applicationContext;
        this.cursor = cursor;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recipeListItemView = LayoutInflater.from(applicationContext).inflate(R.layout.recipe_list_item_layout, parent, false);
        TextView tvRecipeName = recipeListItemView.findViewById(R.id.tv_recipe_name);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(tvRecipeName);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bind(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
