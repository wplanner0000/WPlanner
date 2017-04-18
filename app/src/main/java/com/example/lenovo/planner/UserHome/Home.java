package com.example.lenovo.planner.UserHome;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.Adapters.CustomListAdapter;
import com.example.lenovo.planner.Adapters.ShoplistAdapter;
import com.example.lenovo.planner.Adapters.categoryAdapter;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    ListView lv;
    userhome userho;
    UserDetails userDetails;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lv=(ListView) view.findViewById(R.id.listview);

        userDetails = new UserDetails(getActivity());
        userho =(userhome) getActivity();
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        userho.setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("PhotoGrapher"));
        tabLayout.addTab(tabLayout.newTab().setText("Music"));
        tabLayout.addTab(tabLayout.newTab().setText("Catering"));
        tabLayout.addTab(tabLayout.newTab().setText("Decorations"));
        tabLayout.addTab(tabLayout.newTab().setText("Venue"));
        tabLayout.addTab(tabLayout.newTab().setText("Bakery"));
        tabLayout.addTab(tabLayout.newTab().setText("Clothing"));
        tabLayout.addTab(tabLayout.newTab().setText("GiftShop"));
        tabLayout.addTab(tabLayout.newTab().setText("Saloon"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final categoryAdapter adapter = new categoryAdapter(userho.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0)
                {
                    ((userhome) getActivity()).setTit("Photographer");
                }
                else if(tab.getPosition()==1)
                {
                    ((userhome) getActivity()).setTit("Music");
                }
                else if(tab.getPosition()==2)
                {
                    ((userhome) getActivity()).setTit("Catering");
                }
                else if(tab.getPosition()==3)
                {
                    ((userhome) getActivity()).setTit("Decorations");
                }
                else if(tab.getPosition()==4)
                {
                    ((userhome) getActivity()).setTit("Venue");
                }
                else if(tab.getPosition()==5)
                {
                    ((userhome) getActivity()).setTit("Bakery");
                }
                else if(tab.getPosition()==6)
                {
                    ((userhome) getActivity()).setTit("Clothing");
                }
                else if(tab.getPosition()==7)
                {
                    ((userhome) getActivity()).setTit("GiftShop");
                }
                else if(tab.getPosition()==8)
                {
                    ((userhome) getActivity()).setTit("Saloon");
                }





            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        return view;
    }

}
