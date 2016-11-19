package com.portiq.www.portiq.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.portiq.www.portiq.R;


public class OrderFragment extends Fragment {

    private static final String TAG = "OrderFragment";



    private LinearLayoutManager mManager;

    private TextView questionText;
    private Button answerButton;
    private ProgressBar answerSpinner;
    private EditText answerText;
    private ImageView logoView;


    private BroadcastReceiver answerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


        }
    };

    public OrderFragment() {}

//    private void questionRequest() {
//        WebService.getShipments(getActivity(), ((MainActivity) getActivity()).getCurQuestion());
//    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        answerText = (EditText) rootView.findViewById(R.id.answer_text);
        answerButton = (Button) rootView.findViewById(R.id.answer_button);
        answerSpinner = (ProgressBar) rootView.findViewById(R.id.answer_spinner);
        questionText = (TextView) rootView.findViewById(R.id.question_text);
        logoView = (ImageView) rootView.findViewById(R.id.logo_view);


//        getActivity().registerReceiver(answerReceiver, new IntentFilter("nextQuestion"));

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
//        try {
//            getActivity().unregisterReceiver(answerReceiver);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
