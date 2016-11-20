package com.portiq.www.portiq.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.portiq.www.portiq.Constants;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class WebService {
    private static final String TAG = "WebService";

    private WebService(){

    }


    public static void getPorts(Context context, String company) {
        Intent intent = new Intent(context, HttpService.class);
        intent.setAction(Constants.PORT_API);
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
    public static void getShipments(Context context, String portName) {
        if (portName == null) {
            return;
        }

        Intent intent = new Intent(context, HttpService.class);
        intent.setAction(Constants.SCHEDULE_API);
        intent.putExtra("port", portName);
        context.startService(intent);
    }


}
