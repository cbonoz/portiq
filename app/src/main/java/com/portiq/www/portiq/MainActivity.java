package com.portiq.www.portiq;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.portiq.www.portiq.fragment.OrderFragment;
import com.portiq.www.portiq.fragment.ProfileFragment;
import com.portiq.www.portiq.fragment.CalendarFragment;
import com.portiq.www.portiq.models.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    protected User currentUser = null;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    private void loadMainUI() {
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each section
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new ProfileFragment(),
                    new OrderFragment(),
                    new CalendarFragment()
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_profile),
                    getString(R.string.heading_order),
                    getString(R.string.heading_calendar)
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
    private EditText roleText;

    private ProgressBar loginSpinner;

    private Handler handler = new Handler();

    private void loadSignInUI() {
        setContentView(R.layout.activity_sign_in);
        userText = (EditText) findViewById(R.id.userid);
        passText = (EditText) findViewById(R.id.password);
        companyText = (EditText) findViewById(R.id.company);
        roleText = (EditText) findViewById(R.id.position);
        loginSpinner = (ProgressBar) findViewById(R.id.login_spinner);
//        companyText = (EditText)

        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userText.getText().toString();
                String company = companyText.getText().toString();
                String position = roleText.getText().toString();

                loginSpinner.setVisibility(View.VISIBLE);

                User u = new User(name, "", position, company);
                setCurrentUser(u);
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

        // TODO: Add registration.
        setCurrentUser(null);

        if (currentUser == null) {
            loadSignInUI();
        } else {
            loadMainUI();
        }
    }

}
