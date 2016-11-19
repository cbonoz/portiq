package com.portiq.www.portiq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.portiq.www.portiq.fragment.PortObjectFragment;

/**
 * Created by cbono on 11/19/16.
 */
// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class PortCollectionPagerAdapter extends FragmentStatePagerAdapter {


    public PortCollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PortObjectFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(PortObjectFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
