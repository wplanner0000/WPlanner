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
import com.example.lenovo.planner.APIlinks;
import com.example.lenovo.planner.Adapters.ShoplistAdapter;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class results extends Fragment {

    ArrayList<String> shopname;
    ArrayList<String> shopaddress;
    ArrayList<String> imageurl;
    ListView shop;
    UserDetails userDetails;
    public results() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        shop=(ListView) view.findViewById(R.id.shopview);
        userDetails = new UserDetails(getActivity());
        final userhome userho = (userhome) getActivity();
/*        shopname =new ArrayList<>();
        shopaddress = new ArrayList<>();
        imageurl =new ArrayList<>();
        shopname.add("Ankit Photographers");
        shopname.add("Mridul Photographers");
        shopname.add("Vivek Photographers");
        shopaddress.add("Kanpur,Uttar Pradesh");
        shopaddress.add("Patna, Bihar");
        shopaddress.add("Begusarai,Bihar");
        imageurl.add("http://www.lanlinglaurel.com/data/out/52/4356932-love-you-pic.png");
        imageurl.add("http://www.lanlinglaurel.com/data/out/52/4356932-love-you-pic.png");
        imageurl.add("http://www.lanlinglaurel.com/data/out/52/4356932-love-you-pic.png");

        ShoplistAdapter shoplistAdapter = new ShoplistAdapter(getActivity(),shopname,shopaddress,imageurl);
        shop.setAdapter(shoplistAdapter);*/

        final String category_id = userDetails.getitem();

        final ArrayList<String> shopnames = new ArrayList<>();
        final ArrayList<String> shopaddresss = new ArrayList<>();
        final ArrayList<String> imageurls = new ArrayList<>();
        StringRequest stringRequest;
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please Wait.....", "Fetching Data......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, APIlinks.shopdetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        if (response.equals("fail") == false) {
                            loading.dismiss();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    shopnames.add(jsonObject.getString("name"));
                                    shopaddresss.add(jsonObject.getString("state"));
                                    imageurls.add(jsonObject.getString("imageurl"));
                                    //Toast.makeText(getActivity(), ""+jsonObject.getString("name")+"      "+jsonObject.getString("state")+"       "+jsonObject.getString("imageurl"), Toast.LENGTH_SHORT).show();
                                }
                                ShoplistAdapter shoplistAdapter = new ShoplistAdapter(getActivity(),shopnames,shopaddresss,imageurls);
                                shop.setAdapter(shoplistAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }




                        }
                        else {
                            loading.dismiss();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getActivity(), "error.toString", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("category_id",category_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long item = id;
                userDetails.setitem1(item);
                userho.resultsingle(view);

            }
        });





        return view;
    }


}
