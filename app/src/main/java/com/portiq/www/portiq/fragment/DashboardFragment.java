package com.portiq.www.portiq.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.portiq.www.portiq.R;
import com.portiq.www.portiq.UserInfo;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment {

    private TextView nameText;
    private TextView companyText;
    private TextView positionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        nameText = (TextView) view.findViewById(R.id.name);
        companyText = (TextView) view.findViewById(R.id.company);
        positionText = (TextView) view.findViewById(R.id.position);

        Log.d(TAG, "Current user email: " + UserInfo.currentUser.user);
        nameText.setText("Registered Name: " + UserInfo.currentUser.user);
        companyText.setText("Company: " + UserInfo.currentUser.company);
        positionText.setText("Role: " + UserInfo.currentUser.position);

        return view;
    }


}
