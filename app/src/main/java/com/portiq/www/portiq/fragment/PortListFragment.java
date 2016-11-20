package com.portiq.www.portiq.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.portiq.www.portiq.PortCollectionPagerAdapter;
import com.portiq.www.portiq.R;
import com.portiq.www.portiq.UserInfo;
import com.portiq.www.portiq.services.WebService;

import java.util.Arrays;
import java.util.List;


public class PortListFragment extends Fragment {

    private static final String TAG = "PortListFragment";

    private LinearLayoutManager mManager;

    private TextView questionText;
    private Button answerButton;
    private ProgressBar answerSpinner;
    private EditText answerText;
    private ImageView logoView;

    private Button refreshButton;
    private ProgressBar spinner;

    private static List<String> ports;

    private BroadcastReceiver portReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = intent.getBooleanExtra("success", false);
            String data = intent.getStringExtra("data");
            Log.d(TAG, "portReceiver: "  + data);
            Log.d(TAG, "success: " + success);
            if (success) {
//                ports = intent.getStringArrayListExtra("ports");
                ports = Arrays.asList("portA", "portB", "portC");
                Log.d(TAG, "portReceiver received: " + ports);
            }
            // TODO: load ports into UI list view.
            spinner.setVisibility(View.INVISIBLE);
        }
    };

    public PortListFragment() {

    }

    private void portRequest() {
        Log.d(TAG, "portRequest");
        spinner.setVisibility(View.VISIBLE);
        WebService.getPorts(getActivity(), UserInfo.currentUser.company);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_port_list, container, false);
        Log.d(TAG, "onCreate PortListFragment");

        spinner = (ProgressBar)rootView.findViewById(R.id.port_spinner);
        spinner.setVisibility(View.INVISIBLE);

        refreshButton = (Button) rootView.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portRequest();
            }

        });

        PortCollectionPagerAdapter mPortCollectionPagerAdapter;
        ViewPager mViewPager;

//        setContentView(R.layout.activity_collection_demo);

        getActivity().registerReceiver(portReceiver, new IntentFilter("getPorts"));

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mPortCollectionPagerAdapter =
                new PortCollectionPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mPortCollectionPagerAdapter);

        portRequest();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
    }

//    private void onAnswerClicked(DatabaseReference questionRef) {
//
//    }
//
//    private void onRateQuestionClicked(DatabaseReference questionRef) {
//
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(portReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
