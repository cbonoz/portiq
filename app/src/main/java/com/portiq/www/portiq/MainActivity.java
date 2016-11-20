package com.portiq.www.portiq;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.portiq.www.portiq.fragment.DashboardFragment;
import com.portiq.www.portiq.fragment.HistoryFragment;
import com.portiq.www.portiq.fragment.ParentPortFragment;
import com.portiq.www.portiq.fragment.PortFragment;
import com.portiq.www.portiq.fragment.PortShipmentFragment;
import com.portiq.www.portiq.models.Port;
import com.portiq.www.portiq.models.Shipment;
import com.portiq.www.portiq.models.User;

public class MainActivity extends AppCompatActivity implements
        PortFragment.OnListFragmentInteractionListener,
        PortShipmentFragment.OnListFragmentInteractionListener {

    @Override
    public void onListFragmentInteraction(Port port) {
        Log.d(TAG, "onListFragmentInteraction port: " + port);
        Log.d(TAG, "Starting PostShipmentFragment with port: " + port.name);
        // Create new fragment and transaction
        ((ParentPortFragment) mPagerAdapter.getItem(0)).launchScheduleFragment(port.name);
//        Fragment newFragment = new PortShipmentFragment();
//        Bundle bundle=new Bundle();
//        bundle.putString("port", port.name);
//        newFragment.setArguments(bundle);
////        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        FragmentTransaction transaction = mPagerAdapter.getItem(0).getChildFragmentManager().beginTransaction();
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack if needed
//        try {
//            transaction.remove(mPagerAdapter.getItem(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        transaction.replace(R.id.parent_port_container, newFragment);
//        transaction.addToBackStack(null);
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
//        // Commit the transaction
//        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(Shipment shipment) {
        Log.d(TAG, "onListFragmentInteraction shipment: " + shipment);
        UserInfo.selectedShipment = shipment;
        // TODO: Launch fragment from here.
        // Create new fragment and transaction
        ((ParentPortFragment) mPagerAdapter.getItem(0)).launchDetailFragment(shipment);
//        Fragment newFragment = new ShipmentDetailFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("shipment", shipment);
//        newFragment.setArguments(bundle);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        try {
//            transaction.remove(mPagerAdapter.getItem(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        FragmentTransaction transaction = mPagerAdapter.getItem(0).getChildFragmentManager().beginTransaction();
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack if needed
//        transaction.replace(R.id.parent_port_container, newFragment);
//        transaction.addToBackStack(null);
//        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
//        // Commit the transaction
//        transaction.commit();

    }

    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    public ViewPager mViewPager;

    private void loadMainUI() {
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new ParentPortFragment(),
                    new DashboardFragment(),
                    new HistoryFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_portlist),
                    getString(R.string.heading_dashboard),
                    getString(R.string.heading_history)
            };
            @Override
            public Fragment getItem(int position) {


                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private Button loginButton;
    private EditText userText;
    private EditText companyText;
    private EditText passText;
    private EditText positionText;

    private TextView registerText;

    private ProgressBar loginSpinner;

    private Handler handler = new Handler();

    public void registerTextOnClick(View view) {
        Log.d(TAG, "Click registerText");

        companyText.setVisibility(View.VISIBLE);
        positionText.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this, "Enter Company and Position", Toast.LENGTH_SHORT).show();
    };

    private void loadSignInUI() {
        setContentView(R.layout.activity_sign_in);
        userText = (EditText) findViewById(R.id.userid);
        passText = (EditText) findViewById(R.id.password);
        companyText = (EditText) findViewById(R.id.company);
        positionText = (EditText) findViewById(R.id.position);
        loginSpinner = (ProgressBar) findViewById(R.id.login_spinner);
        registerText = (TextView) findViewById(R.id.register_text);

        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userText.getText().toString();
                String company =  companyText.getText().toString();
                String position = positionText.getText().toString();

                if (company.equals("")) {
                    company = "WidgetCo";
                }

                if (position.equals("")) {
                    position = "Superadmin";
                }

                if (user.equals("")) {
                    user = "Chris B.";
                }


                loginSpinner.setVisibility(View.VISIBLE);
                UserInfo.currentUser = new User(user, "", position, company);
                Toast.makeText(MainActivity.this, "Logged In!", Toast.LENGTH_LONG).show();
                loadMainUI();
            }
        });

        loginSpinner.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserInfo.currentUser == null) {
            loadSignInUI();
        } else {
            loadMainUI();
        }
    }


}
