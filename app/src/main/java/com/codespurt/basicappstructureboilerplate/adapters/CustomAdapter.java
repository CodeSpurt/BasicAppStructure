package com.codespurt.basicappstructureboilerplate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by CodeSpurt on 23-09-2017.
 */

public abstract class CustomAdapter extends RecyclerView.Adapter {

    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    private void onItemHolderClick(CustomRecycleViewHolder itemHolder) {
        onItemClickListener = getClick();
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, itemHolder.itemView, itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    private void onItemHolderLongClick(CustomRecycleViewHolder itemHolder) {
        onItemLongClickListener = getLongClick();
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(null, itemHolder.itemView, itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public class CustomRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public CustomRecycleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemHolderClick(this);
        }

        @Override
        public boolean onLongClick(View v) {
            onItemHolderLongClick(this);
            return true;
        }
    }

    // listeners
    protected abstract AdapterView.OnItemClickListener getClick();

    protected abstract AdapterView.OnItemLongClickListener getLongClick();
}
