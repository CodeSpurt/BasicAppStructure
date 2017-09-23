package com.codespurt.basicappstructureboilerplate.engine.callbacks;

import com.codespurt.basicappstructureboilerplate.models.fromNetwork.ResponseWrapper;

public interface NetworkCallbackToInteractor {

    void valid(ResponseWrapper response);

//    void valid(List<ResponseWrapper> wrapper);
//
//    void valid(String response);
//
//    void valid(Object response);

    void error(int statusCode);
}