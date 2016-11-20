package com.portiq.www.portiq.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.portiq.www.portiq.R;
import com.portiq.www.portiq.listcontent.ShipmentContent;
import com.portiq.www.portiq.listcontent.SimpleDivider;
import com.portiq.www.portiq.models.PortShipmentRecyclerViewAdapter;
import com.portiq.www.portiq.models.Shipment;
import com.portiq.www.portiq.services.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PortShipmentFragment extends Fragment {
    private static final String TAG = "PortShipmentFragment";

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private String portName;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PortShipmentFragment() {

    }


    private void shipmentRequest() {
        Log.d(TAG, "shipmentRequest");
        recyclerView.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        WebService.getShipments(getActivity(), portName);
    }

    private List<Shipment> shipments = new ArrayList<>();

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

            List<String> shipmentData;
            shipments.clear();
            success = false;
            if (success) {
//                ports = intent.getStringArrayListExtra("ports");
                // TODO: Parse the data into a string array of shipments, and add to shipments array..
                shipmentData = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray shipmentArray = jsonObject.getJSONArray("data");
//                    Log.d(TAG, "shipmentArray: " + shipmentArray.toString());
                    for(int i=0;i<shipmentArray.length();i++) {
//                        JSONArray p = (JSONArray) shipmentArray.get(i);
//                        String scheduleEntry = p.toString().replace("[","").replace("]","");
//                        List<String> items = Arrays.asList(scheduleEntry.split("\\s*,\\s*"));
//                        Log.d(TAG, "items: " + items.toString());
//                        Shipment s = new Shipment(items.get(2), items.get(1), items.get(0));
//                        shipments.add(s);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error fetching schedule", Toast.LENGTH_SHORT).show();
                    return;
                }


            } else {
                List<String> cNum = Arrays.asList("1233241", "23421425", "241241234");
                List<String> timeIn = Arrays.asList("11/20/16 9:30am", "11/21/16 10:30am", "11/22/16 11:30am");
                List<String> timeOut = Arrays.asList("11/20/16 10:30am", "11/21/16 11:30am", "11/22/16 12:30am");

                for (int i = 0; i < cNum.size(); i++) {
                    Shipment s = new Shipment(cNum.get(i), timeIn.get(i), timeOut.get(i), portName);
                    shipments.add(s);
                }
//                shipmentData = Arrays.asList("shipment A", "shipment B", "shipment C");
            }

            Log.d(TAG, "shipmentReceiver received: " + shipments);
            recyclerView.setAdapter(new PortShipmentRecyclerViewAdapter(shipments, mListener));
            recyclerView.addItemDecoration(new SimpleDivider(getActivity()));
            // Load ports into UI list view.
            spinner.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ProgressBar spinner;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portschedule_list, container, false);

        spinner = (ProgressBar) view.findViewById(R.id.port_spinner);
        spinner.setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setAdapter(new PortShipmentRecyclerViewAdapter(ShipmentContent.ITEMS, mListener));
        portName = getArguments().getString("port");
        Log.d(TAG, "onCreateView PortShipmentFragment port: " + portName);
        getActivity().registerReceiver(shipmentReceiver, new IntentFilter("getSchedule"));
        Log.d(TAG, "Register shipmentReceiver");
        shipmentRequest();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        try {
            getActivity().unregisterReceiver(shipmentReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        try {
//            getActivity().unregisterReceiver(shipmentReceiver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Shipment item);
    }
}
