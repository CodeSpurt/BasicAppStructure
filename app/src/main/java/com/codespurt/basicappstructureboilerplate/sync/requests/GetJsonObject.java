package com.codespurt.basicappstructureboilerplate.sync.requests;

import com.codespurt.basicappstructureboilerplate.engine.callbacks.NetworkCallbackToInteractor;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.NetworkCallbackToParser;
import com.codespurt.basicappstructureboilerplate.models.fromNetwork.ResponseWrapper;
import com.codespurt.basicappstructureboilerplate.models.toNetwork.User;
import com.codespurt.basicappstructureboilerplate.sync.NetworkManagerVolley;
import com.codespurt.basicappstructureboilerplate.sync.RequestURLs;
import com.codespurt.basicappstructureboilerplate.sync.ResponseManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetJsonObject {

    private NetworkCallbackToInteractor apiCallback;

    public GetJsonObject(User user, NetworkCallbackToInteractor callback) {
        this.apiCallback = callback;
        if (user != null) {
            String params = ResponseManager.getGsonInstance().toJson(user);
            hitService(params);
        } else
            hitService("");
    }

    private void hitService(String params) {
        NetworkManagerVolley.getInstance().hitServiceGetObject(params, RequestURLs.JSON_OBJECT_GET, callback);
        // NetworkManagerRetrofit.getInstance().hitServiceGetObject(params, 1, callback);
    }

    // response
    private NetworkCallbackToParser callback = new NetworkCallbackToParser() {

        @Override
        public void gotValidResponseObject(JSONObject response) {
            // convert JSONObject to Java Object
            ResponseWrapper wrapper = ResponseManager.getInstance().parseResponseObject(response);
            if (apiCallback != null) {
                apiCallback.valid(wrapper);
            }
        }

        @Override
        public void gotValidResponseArray(JSONArray response) {

        }

        @Override
        public void gotValidResponseString(String response) {

        }

        @Override
        public void gotValidResponseObject(Object response) {

        }

        @Override
        public void gotInvalidResponse(int statusCode) {
            if (apiCallback != null) {
                apiCallback.error(statusCode);
            }
        }
    };
}