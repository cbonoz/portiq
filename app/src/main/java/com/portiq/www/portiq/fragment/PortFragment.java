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
import com.portiq.www.portiq.UserInfo;
import com.portiq.www.portiq.listcontent.PortContent;
import com.portiq.www.portiq.listcontent.SimpleDivider;
import com.portiq.www.portiq.models.MyPortRecyclerViewAdapter;
import com.portiq.www.portiq.models.Port;
import com.portiq.www.portiq.services.WebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PortFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    private static List<Port> ports = new ArrayList<>();
    private ProgressBar spinner;

    private BroadcastReceiver portReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = intent.getBooleanExtra("success", false);
            String data = intent.getStringExtra("data");
            Log.d(TAG, "portReceiver: "  + data);
            Log.d(TAG, "success: " + success);
            List<String> portData;
            if (success) {
//                ports = intent.getStringArrayListExtra("ports");
                // TODO: Parse the data into a string array of ports
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray portArray=jsonObject.getJSONArray("data");
                    portData = new ArrayList<>();
                    for(int i=0;i<portArray.length();i++) {
                        String p = (String) portArray.get(i);
                        portData.add(p.replace("\"",""));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                portData = Arrays.asList("Laem Chabang", "Vung Tau", "Xiamen", "Tokyo", "Los Angeles", "Kobe", "Nagoya", "Shimizu", "Oakland", "Hong Kong");
                Toast.makeText(getActivity(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
            ports.clear();
            for (String s : portData) {
                ports.add(new Port(s));
            }

            Log.d(TAG, "portReceiver received: " + ports);
            recyclerView.setAdapter(new MyPortRecyclerViewAdapter(ports, mListener));
            recyclerView.addItemDecoration(new SimpleDivider(getActivity()));
            // Load ports into UI list view.
            spinner.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    };

    private void portRequest() {
        Log.d(TAG, "portRequest");
        recyclerView.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
        WebService.getPorts(getActivity(), UserInfo.currentUser.company);
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PortFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_port_list, container, false);

//        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//
//        }

        spinner = (ProgressBar) view.findViewById(R.id.port_spinner);
        spinner.setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setAdapter(new MyPortRecyclerViewAdapter(PortContent.ITEMS, mListener));

        getActivity().registerReceiver(portReceiver, new IntentFilter("getPorts"));
        Log.d(TAG, "Register portReceiver");
        portRequest();


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
            getActivity().unregisterReceiver(portReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        try {
//            getActivity().unregisterReceiver(portReceiver);
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
        void onListFragmentInteraction(Port item);
    }
}
