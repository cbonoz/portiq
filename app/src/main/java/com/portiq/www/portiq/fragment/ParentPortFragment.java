package com.portiq.www.portiq.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.portiq.www.portiq.R;
import com.portiq.www.portiq.models.Shipment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentPortFragment extends Fragment {
    private static final String TAG = "ParentPortFragment";

    public ParentPortFragment() {
        // Required empty public constructor
    }

//    private final Fragment[] mFragments = new Fragment[] {
//            new PortFragment(),
//            new PortShipmentFragment(),
//            new ShipmentDetailFragment()
//    };

    private void loadFragmentIndex(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
//        if (!fragment.isAdded()) {
//            transaction.add(R.id.parent_port_container, fragment).commit();
//        } else {
        transaction.replace(R.id.parent_port_container, fragment).commit();

    }

    public void launchScheduleFragment(String portName) {
        Fragment newFragment = new PortShipmentFragment();
        Bundle bundle=new Bundle();
        bundle.putString("port", portName);
        newFragment.setArguments(bundle);
        loadFragmentIndex(newFragment);
    }

    public void launchDetailFragment(Shipment shipment) {
        Fragment newFragment = new ShipmentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("shipment", shipment);
        newFragment.setArguments(bundle);
        loadFragmentIndex(newFragment);
    }

    public void launchPortFragment() {
        Fragment newFragment = new PortFragment();
        loadFragmentIndex(newFragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent_port, container, false);

        launchPortFragment();

        return view;
    }


}
