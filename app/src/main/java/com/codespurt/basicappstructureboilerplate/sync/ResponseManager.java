package com.codespurt.basicappstructureboilerplate.sync;

import com.codespurt.basicappstructureboilerplate.models.fromNetwork.ResponseWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class ResponseManager {

    private static ResponseManager instance = null;
    private static Gson gson;

    public static int WEB_SERVICE_EXCEPTION = 8001;

    // singleton
    private ResponseManager() {

    }

    public static synchronized ResponseManager getInstance() {
        if (instance == null) {
            instance = new ResponseManager();
        }
        return instance;
    }

    public static synchronized Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public ResponseWrapper parseResponseObject(JSONObject response) {
        ResponseWrapper wrapper = getGsonInstance().fromJson(response.toString(), ResponseWrapper.class);
        return wrapper;
    }

    public List<ResponseWrapper> parseResponseArray(JSONArray response) {
        Type listType = new TypeToken<List<ResponseWrapper>>() {
        }.getType();
        List<ResponseWrapper> wrapper = (List<ResponseWrapper>) getGsonInstance().fromJson(response.toString(), listType);
        return wrapper;
    }
}