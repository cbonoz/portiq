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

import com.portiq.www.portiq.fragment.HistoryFragment;
import com.portiq.www.portiq.fragment.PortListFragment;
import com.portiq.www.portiq.fragment.ProfileFragment;
import com.portiq.www.portiq.models.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    private void loadMainUI() {
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new PortListFragment(),
                    new ProfileFragment(),
                    new HistoryFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_portlist),
                    getString(R.string.heading_profile),
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

    private void loadSignInUI() {
        setContentView(R.layout.activity_sign_in);
        userText = (EditText) findViewById(R.id.userid);
        passText = (EditText) findViewById(R.id.password);
        companyText = (EditText) findViewById(R.id.company);
        positionText = (EditText) findViewById(R.id.position);
        loginSpinner = (ProgressBar) findViewById(R.id.login_spinner);

        registerText = (TextView) findViewById(R.id.register_text);
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click registerText");
                companyText.setVisibility(View.VISIBLE);
                positionText.setVisibility(View.VISIBLE);
            }
        });


        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userText.getText().toString();
                String company = companyText.getText().toString();
                String position = positionText.getText().toString();

                loginSpinner.setVisibility(View.VISIBLE);

                UserInfo.currentUser = new User(name, "", position, company);

                Toast.makeText(MainActivity.this, "Logged In!", Toast.LENGTH_LONG).show();
//                handler.postDelayed()
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
