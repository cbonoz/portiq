package com.portiq.www.portiq.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.portiq.www.portiq.R;
import com.portiq.www.portiq.models.Shipment;
import com.portiq.www.portiq.services.WebService;

import java.util.List;

/**
 * Created by cbono on 11/19/16.
 */

// Instances of this class are fragments representing a single
// object in our collection.
public class PortObjectFragment extends Fragment {
    private static final String TAG = "PortObjectFragment";
    public static final String ARG_OBJECT = "object";

    private String portId = null;

    public static List<Shipment> shipmentList;
    private static Gson gson = new Gson();

    private void shipmentRequest() {
        WebService.getShipments(getActivity(), portId);
    }

    private BroadcastReceiver shipmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = intent.getBooleanExtra("success", false);
            String data = intent.getStringExtra("data");
            Log.d(TAG, "shipmentReceiver: " + data);
            Log.d(TAG, "success: " + success);
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                shipmentList.clear();
//                if (success) {
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jObj = jsonArray.getJSONObject(i);
//                        gson.fromJson(jObj, User.class);
//                        shipmentList.add()
//                    }
//                     =
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            // TODO: Load shipment data into UI list view.
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.port_collection_object, container, false);
        Bundle args = getArguments();

        getActivity().registerReceiver(shipmentReceiver, new IntentFilter("getSchedule"));

        ((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(shipmentReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
