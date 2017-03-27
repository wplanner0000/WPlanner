package com.example.lenovo.planner.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lenovo.planner.profile.userprofile;
import com.example.lenovo.planner.profile.vendorprofileview;


public  class profileadapter extends FragmentStatePagerAdapter {
    int Numoftabs;
    public profileadapter(FragmentManager fm, int numoftabs)
    {
        super(fm);
        this.Numoftabs = numoftabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                userprofile tab1 = new userprofile();
                return tab1;
            case 1:
                vendorprofileview tab2 = new vendorprofileview();
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
