package com.codespurt.basicappstructureboilerplate.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.models.adapter.SamplePojo;
import com.codespurt.basicappstructureboilerplate.views.custom.CustomTextView;

import java.util.ArrayList;

/**
 * Created by CodeSpurt on 23-09-2017.
 */

public class LinearLayoutAdapter extends CustomAdapter {

    private Activity activity;
    private ArrayList<SamplePojo> pojo;
    private int screenWidth;

    public LinearLayoutAdapter(Activity activity, ArrayList<SamplePojo> pojo) {
        this.activity = activity;
        this.pojo = pojo;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.recycler_item, parent, false);
        Holder dataObjectHolder = new Holder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Holder myHolder = (Holder) holder;
        myHolder.name.setText(pojo.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return pojo.size();
    }

    @Override
    protected AdapterView.OnItemClickListener getClick() {
        return clickListener;
    }

    @Override
    protected AdapterView.OnItemLongClickListener getLongClick() {
        return longClickListener;
    }

    // view holder
    public class Holder extends CustomAdapter.CustomRecycleViewHolder {
        private CustomTextView name;

        public Holder(View itemView) {
            super(itemView);
            name = (CustomTextView) itemView.findViewById(R.id.rv_item);
        }
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        }
    };

    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            return false;
        }
    };
}
