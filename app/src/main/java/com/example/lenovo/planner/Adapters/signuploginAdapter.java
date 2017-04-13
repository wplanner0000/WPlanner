package com.example.lenovo.planner.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.planner.applicationstart.fragment_signup;
import com.example.lenovo.planner.applicationstart.login_fragment;

/**
 * Created by LENOVO on 4/12/2017.
 */

public class signuploginAdapter extends FragmentStatePagerAdapter {
    int Numoftabs;
    public signuploginAdapter(FragmentManager fm, int numoftabs)
    {
        super(fm);
        this.Numoftabs = numoftabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                login_fragment tab1 = new login_fragment();
                return tab1;
            case 1:
                fragment_signup tab2 = new fragment_signup();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return Numoftabs;
    }
}
