package com.codespurt.basicappstructureboilerplate.engine.callbacks;

import org.json.JSONArray;
import org.json.JSONObject;

public interface NetworkCallbackToParser {

    void gotValidResponseObject(JSONObject response);

    void gotValidResponseArray(JSONArray response);

    void gotValidResponseString(String response);

    void gotValidResponseObject(Object response);

    void gotInvalidResponse(int statusCode);
}