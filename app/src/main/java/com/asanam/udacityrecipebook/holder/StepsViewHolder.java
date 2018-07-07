package com.asanam.udacityrecipebook.holder;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.db.DBContract;
import com.asanam.udacityrecipebook.fragments.RecipeDetailsFragment;

public class StepsViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private RecipeDetailsFragment.StepSelectionListener listener;

    public StepsViewHolder(View itemView, RecipeDetailsFragment.StepSelectionListener listener) {
        super(itemView);
        this.itemView = itemView;
        this.listener = listener;
    }

    public void bind(final Cursor cursor) {
        TextView tvDetails = itemView.findViewById(R.id.tv_details);
        final long stepId = cursor.getLong(cursor.getColumnIndex(DBContract.StepTable.COLUMN_ID));
        final long recipeId = cursor.getLong(cursor.getColumnIndex(DBContract.StepTable.COLUMN_RECIPE_ID));
        String shortDescription = cursor.getString(cursor.getColumnIndex(DBContract.StepTable.COLUMN_SHORT_DESCRIPTION));
        tvDetails.setText(shortDescription);
        tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelectStep(recipeId, stepId);
            }
        });
    }
}
