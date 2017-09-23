package com.codespurt.basicappstructureboilerplate.mvp.engine;

import com.mvp.MVP;
import com.mvp.Presenter;
import com.mvp.View;

@MVP
public interface Login {

    @Presenter
    void checkCredentialsP(String[] params);

    @View
    void loginSuccessV();

    @View
    void loginFailureV();
}