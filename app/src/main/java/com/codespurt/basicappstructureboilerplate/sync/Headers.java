package com.codespurt.basicappstructureboilerplate.sync;

/**
 * Created by CodeSpurt on 11-08-2017.
 */

public class Headers {

    public static boolean areHeadersRequired = true;

    public static int NUMBER_OF_HEADERS = 3;

    public static String[] HEADER = {
            "Content-Type",
            "Application-Type",
            "Device-Token"
    };

    public static String[] HEADER_BODY = {
            "application/json",
            "ANDROID",
            "123456"
    };
}
