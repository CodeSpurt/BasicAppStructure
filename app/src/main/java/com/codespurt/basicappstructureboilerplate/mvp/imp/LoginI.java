package com.codespurt.basicappstructureboilerplate.mvp.imp;

import com.codespurt.basicappstructureboilerplate.engine.callbacks.NetworkCallbackToInteractor;
import com.codespurt.basicappstructureboilerplate.models.fromNetwork.ResponseWrapper;
import com.codespurt.basicappstructureboilerplate.models.toNetwork.User;
import com.codespurt.basicappstructureboilerplate.mvp.engine.LoginInteractor;
import com.codespurt.basicappstructureboilerplate.mvp.engine.OnLoginInteractedListener;
import com.codespurt.basicappstructureboilerplate.sync.requests.GetJsonObject;
import com.codespurt.basicappstructureboilerplate.utils.Preferences;
import com.codespurt.basicappstructureboilerplate.utils.Validations;

public class LoginI implements LoginInteractor {

    private Validations validations;
    private Preferences preferences;

    // services
    private GetJsonObject getJsonObject;

    // service callbacks
    private NetworkCallbackToInteractor callback;

    LoginI() {
        validations = new Validations();
        preferences = new Preferences();
    }

    @Override
    public void checkCredentialsP(String[] params, final OnLoginInteractedListener listener) {
        if (true) {
            // service response - json object
            callback = new NetworkCallbackToInteractor() {
                @Override
                public void valid(ResponseWrapper response) {

                }

                @Override
                public void error(int statusCode) {

                }
            };
            setParams(params);
            listener.loginSuccessV();
        } else {
            listener.loginFailureV();
        }
    }

    // add params to send to server
    private void setParams(String[] params) {
        User user = new User();
        user.setEmail(params[0]);
        user.setPassword(params[1]);
        hitService(user);
    }

    // call service
    private void hitService(User user) {
        // getJsonObject = new GetJsonObject(user, callback);
    }
}