package com.example.lenovo.planner.UserHome;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    String txt[]=new String[]{"Photographer", "Music", "Catering", "Decoration", "Venue", "Bakery", "Clothing", "GiftShop", "Saloon"};
    ListView lv;

    Integer[] imageId={R.drawable.photographys,R.drawable.musics,R.drawable.caterings,R.drawable.decorationss,R.drawable.venues,R.drawable.bakerys,R.drawable.clothings,R.drawable.giftshops,R.drawable.saloons};
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


        CustomListAdapter adapter=new CustomListAdapter(getActivity(), txt, imageId);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int item = position+1;
                userDetails.setitem(item+"");
                //Toast.makeText(getActivity(), ""+item, Toast.LENGTH_SHORT).show();
                userho.result(view);


            }
        });
        return view;
    }

}
