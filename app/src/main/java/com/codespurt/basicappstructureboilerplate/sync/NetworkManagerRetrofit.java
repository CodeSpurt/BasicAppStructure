package com.codespurt.basicappstructureboilerplate.sync;

import android.content.Context;
import android.util.Log;

import com.codespurt.basicappstructureboilerplate.engine.callbacks.NetworkCallbackToParser;
import com.codespurt.basicappstructureboilerplate.models.fromNetwork.ResponseWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by CodeSpurt on 12-08-2017.
 */

public class NetworkManagerRetrofit {

    private static final String TAG = "NetworkManagerResponse";

    private static NetworkManagerRetrofit instance = null;
    private Retrofit retrofit;
    private int REQUEST_TIME_OUT = 30 * 1000; // 30 seconds

    // singleton
    private NetworkManagerRetrofit(Context context) {

    }

    public static synchronized NetworkManagerRetrofit getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkManagerRetrofit(context);
        }
        return instance;
    }

    public static synchronized NetworkManagerRetrofit getInstance() {
        if (instance == null) {
            throw new IllegalStateException(NetworkManagerRetrofit.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    private Retrofit getRetrofitObject() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RequestURLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // get json object
    public void hitServiceGetObject(final String params, int requestId, final NetworkCallbackToParser callback) {
        if (returnCall(requestId) != null) {
            Call call = returnCall(requestId);
            call.enqueue(new Callback() {

                @Override
                public void onResponse(Call call, Response response) {
                    if (response.toString() != null) {
                        Log.d(TAG, "Get Response : " + response.toString());
                        ResponseWrapper wrapper = (ResponseWrapper) response.body();
                        // send callback to GetJsonObject class
                        callback.gotValidResponseObject(response.body());
                    } else
                        callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
                }

                @Override
                public void onFailure(Call call, Throwable throwable) {
                    Log.d(TAG, throwable.getMessage());
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
                }
            });
        } else {
            callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
        }
    }

    private Call returnCall(int requestId) {
        NetworkCallbackRetrofit mInterface = getRetrofitObject().create(NetworkCallbackRetrofit.class);
        switch (requestId) {
            case 1:
                return mInterface.getRequest();
        }
        return null;
    }

    private interface NetworkCallbackRetrofit {

        @GET(RequestURLs.JSON_OBJECT_GET)
        Call<ResponseWrapper> getRequest();

        @retrofit2.http.Headers({"Content-Type:application/json", "Application-Type:ANDROID"})
        @POST(RequestURLs.JSON_OBJECT_POST)
        Call<ResponseWrapper> postRequest();
    }
}