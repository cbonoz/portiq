package com.portiq.www.portiq.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.portiq.www.portiq.UserInfo;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.action;
import static android.content.ContentValues.TAG;
import static com.portiq.www.portiq.Constants.PORT_API;
import static com.portiq.www.portiq.Constants.SCHEDULE_API;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class HttpService extends IntentService {


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    public HttpService() {
        super("HttpService");
    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent Action: " + action);
        if (intent != null) {
            final String action = intent.getAction();
            final Request request;
            final String company = UserInfo.currentUser.company;
            switch (action) {
                case PORT_API:
                    Log.d(TAG, "Retrieving ports for: " + company);
                    try {
                    URL portUrl = new URL(PORT_API);
                    request = new Request.Builder()
                            .url(portUrl)
                            .build();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    break;
                case SCHEDULE_API:
                    String portName = intent.getStringExtra("port");
                    String jsonString =  "{'port':'" + portName + "', 'company': '" + company + "'}";
                    Log.d(TAG, "Retrieving schedule: " + jsonString);
                    try {
                        URL scheduleUrl = new URL(SCHEDULE_API);
                        request = new Request.Builder()
                                .url(scheduleUrl)
                                .build();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    break;
                default:
                    Log.e(TAG, "Action '" + action + "' not recognized in WebService");
                    return;
            }

//            try {
//                String response = post(action, "");
//                Log.d(TAG, response);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            // Run the http request.
            client.newCall(request).enqueue(new Callback() {

                // Failure case
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, call.request() + " request failed.");
                    e.printStackTrace();
                    final Intent i;
                    switch (action) {
                        case SCHEDULE_API:
                            i = new Intent().setAction("getSchedule");
                            break;
                        case PORT_API:
                            i = new Intent().setAction("getPorts");
                            break;
                        default:
                            i = null;
                            break;
                    }
                    // Success will be interpreted as false
                    sendBroadcast(i);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "onResponse - but not successful");
                        throw new IOException("Unexpected code " + response);
                    }

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.d(TAG, (responseHeaders.name(i) + ": " + responseHeaders.value(i)));
                    }
                    final String responseStr = response.body().string();
                    Log.d(TAG, "Response: " + responseStr);

                    // GitUser user = gson.fromJson(response.body().charStream(), GitUser.class);

                    // TODO: Add action-specific response behavior here.
                    Intent i = null;
                    switch (action) {
                        case SCHEDULE_API:
                            i = new Intent().setAction("getSchedule");
                            i.putExtra("data", responseStr);
                            break;
                        case PORT_API:
                            i = new Intent().setAction("getPorts");
                            i.putExtra("data", responseStr);
                            break;
                        default:
                            Log.d(TAG, "onResponse done: " + action);
                            break;
                    }

                    if (i != null) {
                        // If we got here, then the response is valid.
                        i.putExtra("success", true);
                        sendBroadcast(i);
                    }
                }
            });
        }
    }
}
