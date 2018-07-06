package com.asanam.udacityrecipebook.holder;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.db.DBContract;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private View itemView;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(Cursor cursor) {
        String recipeName = cursor.getString(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_NAME));
        TextView tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        tvRecipeName.setText(recipeName);
    }
}
