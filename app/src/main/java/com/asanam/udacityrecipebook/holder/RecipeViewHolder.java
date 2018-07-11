package com.asanam.udacityrecipebook.holder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.View;
import android.widget.TextView;

import com.asanam.udacityrecipebook.DetailsActivity;
import com.asanam.udacityrecipebook.MainActivity;
import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.TabletDetailsActivity;
import com.asanam.udacityrecipebook.db.DBContract;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    private View itemView;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(Cursor cursor) {
        long aLong = cursor.getLong(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_RECIPE_ID));
        itemView.setOnClickListener(new RecipeClickListener(aLong));
        String recipeName = cursor.getString(cursor.getColumnIndex(DBContract.RecipeTable.COLUMN_NAME));
        TextView tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        tvRecipeName.setText(recipeName);
    }

    private class RecipeClickListener implements View.OnClickListener {
        private long aLong;

        public RecipeClickListener(long aLong) {
            this.aLong = aLong;
        }

        @Override
        public void onClick(View view) {

            Context context = itemView.getContext();
            Intent intent = null;
            if (context.getResources().getBoolean(R.bool.is_tablet)) {
                intent = new Intent(context, TabletDetailsActivity.class);
            } else {
                intent = new Intent(context, DetailsActivity.class);
            }
            intent.putExtra("RECIPE_ID", aLong);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
    }
}
