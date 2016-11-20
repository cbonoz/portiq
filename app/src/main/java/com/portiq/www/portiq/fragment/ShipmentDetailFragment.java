package com.portiq.www.portiq.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.portiq.www.portiq.R;
import com.portiq.www.portiq.models.Shipment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShipmentDetailFragment extends Fragment {
    private static final String TAG = "ShipmentDetailFragment";

    public ShipmentDetailFragment() {
        // Required empty public constructor
    }
    private TextView nameText;
    private TextView containerText;
    private TextView inText;
    private TextView outText;

    private Shipment shipment;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipment_detail, container, false);
        Log.d(TAG, TAG + " onCreateView");
        shipment = (Shipment) getArguments().getSerializable("shipment");
//        shipment = UserInfo.selectedShipment;
        nameText = (TextView) view.findViewById(R.id.shipment_name);
        containerText = (TextView) view.findViewById(R.id.container_text);
        inText = (TextView) view.findViewById(R.id.in_text);
        outText = (TextView) view.findViewById(R.id.out_text);

        containerText.setText("Container: " + shipment.containerNum);
        inText.setText("Planned Arrival: " + shipment.timeIn);
        outText.setText("Planned Departure" + shipment.timeOut);
        nameText.setText(shipment.port + "\n---\n");

        btn = (Button) view.findViewById(R.id.back_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ParentPortFragment) getParentFragment()).launchPortFragment();
            }
        });

        return view;
    }

}
