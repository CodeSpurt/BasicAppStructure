package com.codespurt.basicappstructureboilerplate.views.fragments;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.adapters.CustomAdapter;
import com.codespurt.basicappstructureboilerplate.adapters.LinearLayoutAdapter;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.PermissionCallback;
import com.codespurt.basicappstructureboilerplate.models.adapter.SamplePojo;
import com.codespurt.basicappstructureboilerplate.views.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CodeSpurt on 23-09-2017.
 */

public class HistoryFragment extends BaseFragment {

    @Bind(R.id.rv_view)
    RecyclerView recyclerViewMain;

    private ArrayList<SamplePojo> pojo;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomAdapter adapter;

    @Override
    protected Context getContextForFragment() {
        return getContext();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void subOnCreateView() {
        ButterKnife.bind(this, getCurrentView());

        setupList();
    }

    @Override
    protected void setFontsToViews() {

    }

    @Override
    protected boolean isPermissionsRequired() {
        return false;
    }

    @Override
    protected int getPermissionCode() {
        return 0;
    }

    @Override
    protected PermissionCallback getPermissionCallback() {
        return null;
    }

    @Override
    protected void enableClicks() {

    }

    @Override
    protected void disableClicks() {

    }

    private void setupList() {
        pojo = new ArrayList<>();

        // sample data
        for (int countr = 0; countr < 10; countr++) {
            SamplePojo samplePojo = new SamplePojo();
            samplePojo.setItemName("Item " + String.valueOf(countr + 1));
            pojo.add(samplePojo);
        }

        setAdapter(LAYOUT_MANAGER_LINEAR);
    }

    private void setAdapter(int type) {
        switch (type) {
            // linear
            case LAYOUT_MANAGER_LINEAR:
                recyclerViewMain.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getContextForFragment());
                recyclerViewMain.setLayoutManager(mLayoutManager);
                adapter = new LinearLayoutAdapter(getActivity(), pojo);
                break;
            // grid
            case LAYOUT_MANAGER_GRID:
                int ITEMS_PER_ROW_GRID = 2;
                recyclerViewMain.setHasFixedSize(true);
                mLayoutManager = new GridLayoutManager(getContextForFragment(), ITEMS_PER_ROW_GRID);
                recyclerViewMain.setLayoutManager(mLayoutManager);
                adapter = new LinearLayoutAdapter(getActivity(), pojo);
                break;
            // staggered
            case LAYOUT_MANAGER_STAGGERED:
                int ITEMS_PER_ROW_STAGGERED = 3;
                recyclerViewMain.setHasFixedSize(true);
                mLayoutManager = new StaggeredGridLayoutManager(ITEMS_PER_ROW_STAGGERED, GridLayoutManager.VERTICAL);
                recyclerViewMain.setLayoutManager(mLayoutManager);
                adapter = new LinearLayoutAdapter(getActivity(), pojo);
                break;
        }
        recyclerViewMain.setAdapter(adapter);
    }
}
