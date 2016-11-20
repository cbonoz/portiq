package com.portiq.www.portiq;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by cbono on 11/19/16.
 */

public class Constants {

    private Constants() {

    }

    public static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    // TODO: Replace with valid url
    public static final String BASE_URL = "http://198.58.123.202:9004"; // formerly localhost
    // Mock API endpoints.
    public static final String SCHEDULE_API = BASE_URL + "/getTestSchedule";
    public static final String PORT_API = BASE_URL + "/getTestPorts";
}
