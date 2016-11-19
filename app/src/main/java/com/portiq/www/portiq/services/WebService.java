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
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private static final String SHIPMENTS_API = BASE_URL + "/shipments";
    private static final String CALENDAR_API = BASE_URL + "/schedule";
    private static final String GET_USER_QUESTIONS_API = BASE_URL + "/questions";
    private static final String SET_USER_API = BASE_URL + "/user/set";



    public WebService() {
        super("WebService");
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void getShipments(Context context, int questionId) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(SHIPMENTS_API);
        intent.putExtra("id", questionId);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void getCalendar(Context context, User user) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(CALENDAR_API);
        intent.putExtra("user", user);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void setUser(Context context, User user) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(GET_USER_QUESTIONS_API);
        intent.putExtra("user", user);
        context.startService(intent);
    }

    // Methods above are called externally.
    private static Gson gsonObj = new Gson();

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(TAG, "onHandleIntent Action: " + action);
            User user;
            final Request request;
            switch (action) {
                case CALENDAR_API:
                    user = (User) intent.getSerializableExtra("user");
                    request = new Request.Builder()
                            .url(CALENDAR_API + "/" + user.user)
                            .get()
                            .build();
                    break;
                case SHIPMENTS_API:
                    user = (User) intent.getSerializableExtra("user");
                    request = new Request.Builder()
                            .url(SHIPMENTS_API)
                            .post(RequestBody.create(JSON, gsonObj.toJson(user)))
                            .build();
                    break;
                default:
                    Log.e(TAG, "Action '" + action + "' not recognized in WebService");
                    return;
            }

            // Run the http request.
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, action + " request failed.");
                    e.printStackTrace();
                    Intent i;
                    switch (action) {
                        case SHIPMENTS_API:
//                            i = new Intent().setAction("addQuestion");
//                            sendBroadcast(i);
                            break;
                    }
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
                        case SHIPMENTS_API:
//                            i = new Intent().setAction("setUser");

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
