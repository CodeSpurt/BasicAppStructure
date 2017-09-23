package com.codespurt.basicappstructureboilerplate.sync;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.NetworkCallbackToParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkManagerVolley {

    private static final String TAG = "NetworkManagerResponse";

    private static NetworkManagerVolley instance = null;
    private RequestQueue requestQueue;
    private int REQUEST_TIME_OUT = 30 * 1000; // 30 seconds

    // set REQUEST_ENCODING empty if return default body, else utf-8
    private static final String REQUEST_ENCODING = "";

    // singleton
    private NetworkManagerVolley(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized NetworkManagerVolley getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkManagerVolley(context);
        }
        return instance;
    }

    public static synchronized NetworkManagerVolley getInstance() {
        if (instance == null) {
            throw new IllegalStateException(NetworkManagerVolley.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    // GET - get json object
    public void hitServiceGetObject(final String params, String urlSuffix, final NetworkCallbackToParser callback) {
        String url = RequestURLs.BASE_URL + urlSuffix;

        // attach key-value pairs
        Map<String, Object> jsonParams = new HashMap<>();
        // jsonParams.put("param1", params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, /*null*/ new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.toString() != null) {
                    Log.d(TAG, "Get Response : " + response.toString());
                    callback.gotValidResponseObject(response);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.networkResponse != null) {
                    Log.d(TAG, "Error Response code: " + error.networkResponse.statusCode);
                    callback.gotInvalidResponse(error.networkResponse.statusCode);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }) {
            @Override
            public byte[] getBody() {
                if (REQUEST_ENCODING.trim().equals("")) {
                    return super.getBody();
                } else {
                    try {
                        return params == null ? null : params.getBytes(REQUEST_ENCODING);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (!Headers.areHeadersRequired) {
                    return super.getHeaders();
                } else {
                    return setHeaders();
                }
            }
        };
        requestQueue.add(setJsonObjectRequestPolicy(request));
    }

    // POST - get json object
    public void hitServicePostObject(final String params, String urlSuffix, final NetworkCallbackToParser callback) {
        String url = RequestURLs.BASE_URL + urlSuffix;

        // attach key-value pairs
        Map<String, Object> jsonParams = new HashMap<>();
        // jsonParams.put("param1", params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, /*null*/ new JSONObject(jsonParams), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (response.toString() != null) {
                    Log.d(TAG, "Post Response : " + response.toString());
                    callback.gotValidResponseObject(response);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.networkResponse != null) {
                    Log.d(TAG, "Error Response code: " + error.networkResponse.statusCode);
                    callback.gotInvalidResponse(error.networkResponse.statusCode);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }) {
            @Override
            public byte[] getBody() {
                if (REQUEST_ENCODING.trim().equals("")) {
                    return super.getBody();
                } else {
                    try {
                        return params == null ? null : params.getBytes(REQUEST_ENCODING);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (!Headers.areHeadersRequired) {
                    return super.getHeaders();
                } else {
                    return setHeaders();
                }
            }
        };
        requestQueue.add(setJsonObjectRequestPolicy(request));
    }

    // get json array
    public void hitServiceArray(final String params, String urlSuffix, final NetworkCallbackToParser callback) {
        String url = RequestURLs.BASE_URL + urlSuffix;

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.toString() != null) {
                    Log.d(TAG, "Get Response : " + response.toString());
                    callback.gotValidResponseArray(response);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.networkResponse != null) {
                    Log.d(TAG, "Error Response code: " + error.networkResponse.statusCode);
                    callback.gotInvalidResponse(error.networkResponse.statusCode);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }) {
            @Override
            public byte[] getBody() {
                if (REQUEST_ENCODING.trim().equals("")) {
                    return super.getBody();
                } else {
                    try {
                        return params == null ? null : params.getBytes(REQUEST_ENCODING);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (!Headers.areHeadersRequired) {
                    return super.getHeaders();
                } else {
                    return setHeaders();
                }
            }
        };
        requestQueue.add(setJsonArrayRequestPolicy(request));
    }

    // GET - get string
    public void hitServiceGetString(final String params, String urlSuffix, final NetworkCallbackToParser callback) {
        String url = RequestURLs.BASE_URL + urlSuffix;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.length() != 0) {
                    Log.d(TAG, "Get Response : " + response);
                    callback.gotValidResponseString(response);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.networkResponse != null) {
                    Log.d(TAG, "Error Response code: " + error.networkResponse.statusCode);
                    callback.gotInvalidResponse(error.networkResponse.statusCode);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (REQUEST_ENCODING.trim().equals("")) {
                    return super.getBody();
                } else {
                    try {
                        return params == null ? null : params.getBytes(REQUEST_ENCODING);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (!Headers.areHeadersRequired) {
                    return super.getHeaders();
                } else {
                    return setHeaders();
                }
            }
        };
        requestQueue.add(setStringRequestPolicy(request));
    }

    // POST - get string
    public void hitServicePostString(final String params, String urlSuffix, final NetworkCallbackToParser callback) {
        String url = RequestURLs.BASE_URL + urlSuffix;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.length() != 0) {
                    Log.d(TAG, "Post Response : " + response);
                    callback.gotValidResponseString(response);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.networkResponse != null) {
                    Log.d(TAG, "Error Response code: " + error.networkResponse.statusCode);
                    callback.gotInvalidResponse(error.networkResponse.statusCode);
                } else
                    callback.gotInvalidResponse(ResponseManager.WEB_SERVICE_EXCEPTION);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (REQUEST_ENCODING.trim().equals("")) {
                    return super.getBody();
                } else {
                    try {
                        return params == null ? null : params.getBytes(REQUEST_ENCODING);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (!Headers.areHeadersRequired) {
                    return super.getHeaders();
                } else {
                    return setHeaders();
                }
            }
        };
        requestQueue.add(setStringRequestPolicy(request));
    }

    // set headers
    private HashMap<String, String> setHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        for (int countr = 0; countr < Headers.NUMBER_OF_HEADERS; countr++) {
            headers.put(Headers.HEADER[countr], Headers.HEADER_BODY[countr]);
        }
        return headers;
    }

    private JsonObjectRequest setJsonObjectRequestPolicy(JsonObjectRequest request) {
        RetryPolicy policy = new DefaultRetryPolicy(REQUEST_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        return request;
    }

    private JsonArrayRequest setJsonArrayRequestPolicy(JsonArrayRequest request) {
        RetryPolicy policy = new DefaultRetryPolicy(REQUEST_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        return request;
    }

    private StringRequest setStringRequestPolicy(StringRequest request) {
        RetryPolicy policy = new DefaultRetryPolicy(REQUEST_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        return request;
    }
}