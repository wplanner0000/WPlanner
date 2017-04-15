package com.example.lenovo.planner.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.planner.UserHome.Bakery;
import com.example.lenovo.planner.UserHome.Catering;
import com.example.lenovo.planner.UserHome.Clothing;
import com.example.lenovo.planner.UserHome.Decorations;
import com.example.lenovo.planner.UserHome.Giftshops;
import com.example.lenovo.planner.UserHome.Music;
import com.example.lenovo.planner.UserHome.Photographer;
import com.example.lenovo.planner.UserHome.Saloon;
import com.example.lenovo.planner.UserHome.Venue;
import com.example.lenovo.planner.profile.userprofile;
import com.example.lenovo.planner.profile.vendorprofileview;

/**
 * Created by LENOVO on 4/13/2017.
 */

public class categoryAdapter extends FragmentStatePagerAdapter {
    int Numoftabs;
    public categoryAdapter(FragmentManager fm, int numoftabs)
    {
        super(fm);
        this.Numoftabs = numoftabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Photographer tab1 = new Photographer();
                return tab1;
            case 1:
                Music tab2 = new Music();
                return tab2;
            case 2:
                Catering tab3 = new Catering();
                return tab3;
            case 3:
                Decorations tab4 = new Decorations();
                return tab4;
            case 4:
                Venue tab5 = new Venue();
                return tab5;
            case 5:
                Bakery tab6 = new Bakery();
                return tab6;
            case 6:
                Clothing tab7 = new Clothing();
                return tab7;
            case 7:
                Giftshops tab8 = new Giftshops();
                return tab8;
            case 8:
                Saloon tab9 = new Saloon();
                return tab9;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return Numoftabs;
    }
}

