package com.codespurt.basicappstructureboilerplate.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.PermissionCallback;
import com.codespurt.basicappstructureboilerplate.mvp.engine.LoginPresenter;
import com.codespurt.basicappstructureboilerplate.mvp.engine.LoginView;
import com.codespurt.basicappstructureboilerplate.mvp.imp.LoginP;
import com.codespurt.basicappstructureboilerplate.views.BaseActivity;
import com.codespurt.basicappstructureboilerplate.views.custom.CustomButton;
import com.codespurt.basicappstructureboilerplate.views.custom.CustomEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Login extends BaseActivity implements View.OnClickListener, LoginView {

    @Bind(R.id.coordinatorLayout_login)
    CoordinatorLayout activity_layout;

    @Bind(R.id.iv_app)
    ImageView image;

    @Bind(R.id.et_email)
    CustomEditText email;

    @Bind(R.id.et_password)
    CustomEditText password;

    @Bind(R.id.btn_login)
    CustomButton login;

    private LoginPresenter presenter;

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void subOnCreate() {
        setToolbar(R.string.login);
        ButterKnife.bind(this);

        presenter = new LoginP(this);

        // load image
        download.downloadAndSetImage("http://api.androidhive.info/images/glide/small/bvs.png", image);
    }

    @Override
    protected CoordinatorLayout getLayout() {
        return activity_layout;
    }

    @Override
    protected void setFontsToViews() {

    }

    @Override
    protected boolean hasFragments() {
        return false;
    }

    @Override
    protected boolean hideToolbar() {
        return false;
    }

    @Override
    protected boolean showToolbarBackArrow() {
        return false;
    }

    @Override
    protected boolean disableBackPress() {
        return false;
    }

    @Override
    protected void customBackPress() {

    }

    @Override
    protected boolean showOptionsMenu() {
        return false;
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
        login.setOnClickListener(this);
    }

    @Override
    protected void disableClicks() {
        login.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                alerts.showProgressDialog();
                presenter.checkCredentialsP(new String[]{
                        email.getText().toString(),
                        password.getText().toString()
                });
                break;
        }
    }

    @Override
    public void loginSuccessV() {
        alerts.hideProgressDialog();
        // preferences.save(Preferences.IS_LOGGED_IN, Preferences.TRUE);
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailureV() {
        alerts.hideProgressDialog();
        // preferences.save(Preferences.IS_LOGGED_IN, Preferences.FALSE);
        alerts.showToast(R.string.invalid_credentials);
    }
}