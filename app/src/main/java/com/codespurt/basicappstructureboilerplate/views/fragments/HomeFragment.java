package com.codespurt.basicappstructureboilerplate.views.fragments;

import android.content.Context;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.PermissionCallback;
import com.codespurt.basicappstructureboilerplate.views.BaseFragment;

/**
 * Created by CodeSpurt on 22-09-2017.
 */

public class HomeFragment extends BaseFragment {

    @Override
    protected Context getContextForFragment() {
        return getContext();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void subOnCreateView() {

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
}
