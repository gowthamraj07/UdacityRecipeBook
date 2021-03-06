package com.asanam.udacityrecipebook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.fragments.RecipeDetailsFragment;
import com.asanam.udacityrecipebook.holder.StepsViewHolder;

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {
    private Cursor cursor;
    private Context context;
    private RecipeDetailsFragment.StepSelectionListener listener;

    public StepsAdapter(Cursor cursor, Context context, RecipeDetailsFragment.StepSelectionListener listener) {
        this.cursor = cursor;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.recipe_details_list_item_layout, parent, false);
        return new StepsViewHolder(item, listener);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bind(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
