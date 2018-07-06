package com.asanam.udacityrecipebook.holder;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.asanam.udacityrecipebook.R;
import com.asanam.udacityrecipebook.db.DBContract;

public class StepsViewHolder extends RecyclerView.ViewHolder {
    private View itemView;

    public StepsViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(Cursor cursor) {
        TextView tvDetails = itemView.findViewById(R.id.tv_details);
        String shortDescription = cursor.getString(cursor.getColumnIndex(DBContract.StepTable.COLUMN_SHORT_DESCRIPTION));
        tvDetails.setText(shortDescription);
    }
}
