package com.example.lenovo.planner.UserHome;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.Adapters.ShoplistAdapter;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.profile.profile;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.lenovo.planner.R.drawable.shop;

public class singleshop extends Fragment {
    CircleImageView profileimage;
    TextView oname,contact,category,experience,price,address,longitude,latitude;
    UserDetails userDetails;
    ImageButton editvendor;
    public singleshop() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singleshop, container, false);
        userDetails = new UserDetails(getActivity());
        final int id = (int)userDetails.getitem1();
        profileimage =(CircleImageView) view.findViewById(R.id.imgProfilePicture);
        oname = (TextView) view.findViewById(R.id.oname);
        category = (TextView) view.findViewById(R.id.spn_category);
        contact = (TextView) view.findViewById(R.id.contact);
        experience = (TextView) view.findViewById(R.id.experience);
        price = (TextView) view.findViewById(R.id.price);
        address = (TextView) view.findViewById(R.id.et_address);
        longitude = (TextView) view.findViewById(R.id.longitude);
        latitude = (TextView) view.findViewById(R.id.latitude);
        editvendor = (ImageButton) view.findViewById(R.id.brn_editvendor);

        final String category_id = userDetails.getitem();
        final ArrayList<String> shopnames = new ArrayList<>();
        final ArrayList<String> shopaddresss = new ArrayList<>();
        final ArrayList<String> imageurls = new ArrayList<>();
        StringRequest stringRequest;
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please Wait.....", "Fetching Data......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, "https://wplanner0000.000webhostapp.com/wplanner/shopdetails.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        Log.d("DataBase Response", response);
                        if (!response.equals("0")) {
                            loading.dismiss();

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(id);
                                Picasso.with(getActivity()).load(jsonObject.getString("imageurl")).into(profileimage);
                                oname.setText(jsonObject.getString("name"));
                                contact.setText(jsonObject.getString("contactno"));
                                experience.setText(jsonObject.getString("experience"));
                                price.setText(jsonObject.getString("price"));
                                address.setText(jsonObject.getString("address"));
                                longitude.setText(jsonObject.getString("longitude"));
                                latitude.setText(jsonObject.getString("latitude"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(),e.getMessage()+"", Toast.LENGTH_SHORT).show();

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


        return view;
    }

}
