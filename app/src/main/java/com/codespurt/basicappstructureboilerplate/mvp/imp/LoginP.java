package com.codespurt.basicappstructureboilerplate.mvp.imp;

import com.codespurt.basicappstructureboilerplate.mvp.engine.LoginInteractor;
import com.codespurt.basicappstructureboilerplate.mvp.engine.LoginPresenter;
import com.codespurt.basicappstructureboilerplate.mvp.engine.LoginView;
import com.codespurt.basicappstructureboilerplate.mvp.engine.OnLoginInteractedListener;

public class LoginP implements LoginPresenter, OnLoginInteractedListener {

    private LoginView view;
    private LoginInteractor interactor;

    public LoginP(LoginView view) {
        this.view = view;
        interactor = new LoginI();
    }

    @Override
    public void checkCredentialsP(String[] params) {
        interactor.checkCredentialsP(params, this);
    }

    @Override
    public void loginSuccessV() {
        view.loginSuccessV();
    }

    @Override
    public void loginFailureV() {
        view.loginFailureV();
    }
}