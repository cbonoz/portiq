package com.portiq.www.portiq.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.portiq.www.portiq.models.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.action;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WebService extends IntentService {
    private static final String TAG = "WebService";

    private static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static final String BASE_URL = "http://198.58.123.202:9001"; // formerly localhost
    private static final String SCHEDULE_API = BASE_URL + "/getSchedule";
    private static final String PORT_API = BASE_URL + "/getPorts";

    public WebService() {
        super("WebService");
    }

    public static void getPorts(Context context, String company) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(PORT_API);
        intent.putExtra("company", company);
        context.startService(intent);
        Log.d(TAG, "getPorts");
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void getShipments(Context context, String portId) {
        if (portId == null) {
            return;
        }

        Intent intent = new Intent(context, WebService.class);
        intent.setAction(SCHEDULE_API);
        intent.putExtra("id", portId);
        context.startService(intent);
    }

    // Methods above are called externally.
    private static Gson gsonObj = new Gson();

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent Action: " + action);
        if (intent != null) {
            final String action = intent.getAction();

            User user;
            final Request request;
            switch (action) {
                case PORT_API:
                    user = (User) intent.getSerializableExtra("user");
                    request = new Request.Builder()
                            .url(PORT_API + "/" + user.company)
                            .get()
                            .build();
                    break;
                case SCHEDULE_API:
                    //                            .post(RequestBody.create(JSON, gsonObj.toJson(user)))
                    request = new Request.Builder()
                            .url(SCHEDULE_API)
                            .post(null)
                            .build();
                    break;
                default:
                    Log.e(TAG, "Action '" + action + "' not recognized in WebService");
                    return;
            }

            // Run the http request.
            client.newCall(request).enqueue(new Callback() {

                // Failure case
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, action + " request failed.");
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
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.d(TAG, (responseHeaders.name(i) + ": " + responseHeaders.value(i)));
                    }
                    final String responseStr = response.body().string();
                    Log.d(TAG, "Response: " + responseStr);

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
