package com.example.lenovo.planner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VendorProfile extends AppCompatActivity {
    EditText oname,contact,experiences,prices,citys,districts,states,pincodes,statuss;
    //Bundle extras = getIntent().getExtras();
    //final String uid = extras.getString("uid");
    //final String category_id = extras.getString("category_id");
    //SharedPreferences prefs = getSharedPreferences("MyPref", 0);
    final String uid = "5";    //prefs.getString("uid","0");
    final String category_id = "5";   //prefs.getString("category_id","0");

    String url = "https://wplanner0000.000webhostapp.com/wplanner/vendorprofile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile);
        oname = (EditText) findViewById(R.id.oname);
        contact = (EditText) findViewById(R.id.contact);
        experiences = (EditText) findViewById(R.id.experience);
        prices = (EditText) findViewById(R.id.price);
        citys = (EditText) findViewById(R.id.city);
        districts = (EditText) findViewById(R.id.district);
        states = (EditText) findViewById(R.id.state);
        pincodes = (EditText) findViewById(R.id.pincode);
        statuss = (EditText) findViewById(R.id.status);
    }
    public void submit(View v)
    {
        if (inValid())
        {
            return;
        }
        final String name = oname.getText().toString();
        final String contactno = contact.getText().toString();
        final String price = prices.getText().toString();
        final String status = statuss.getText().toString();
        final String experience = experiences.getText().toString();
        final String city = citys.getText().toString();
        final String district = districts.getText().toString();
        final String state = states.getText().toString();
        final String pincode = pincodes.getText().toString();


        StringRequest stringRequest;
        final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "Registering Vendor......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        if (response.equals("success")) {
                            loading.dismiss();
                            Intent intent2 = new Intent(getApplicationContext(), userhome.class);
                            startActivity(intent2);
                        } else {
                            loading.dismiss();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(VendorProfile.this, "error.toString", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid",uid);
                params.put("category_id", category_id);
                params.put("contactno", contactno);
                params.put("name", name);
                params.put("price", price);
                params.put("status", status);
                params.put("experience", experience);
                params.put("city", city);
                params.put("district", district);
                params.put("state", state);
                params.put("pincode", pincode);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean inValid() {
        String fullname = oname.getText().toString();
        String fullcontactno = contact.getText().toString();
        String fullprice = prices.getText().toString();
        String fullstatus = statuss.getText().toString();
        String fullexperience = experiences.getText().toString();
        String fullcity = citys.getText().toString();
        String fulldistrict = districts.getText().toString();
        String fullstate = states.getText().toString();
        String fullpincode = pincodes.getText().toString();

        if (fullname.isEmpty()) {
            oname.setError("Enter Organisation Name");
            return true;
        }
        if (fullcontactno.isEmpty()) {
            contact.setError("Enter Contact Number");
            return true;
        }
        if (fullprice.isEmpty()) {
            prices.setError("Enter Expected Price");
            return true;
        }
        if (fullexperience.isEmpty()) {
            experiences.setError("Enter Working Experience");
            return true;
        }
        if (fullstatus.isEmpty()) {
            statuss.setError("Enter Status");
            return true;
        }
        if (fullcity.isEmpty()) {
            citys.setError("Enter City");
            return true;
        }
        if (fulldistrict.isEmpty()) {
            districts.setError("Enter District");
            return true;
        }
        if (fullstate.isEmpty()) {
            states.setError("Enter State");
            return true;
        }
        if (fullpincode.isEmpty()) {
            pincodes.setError("Enter Pincode");
            return true;
        }

        return false;
    }
}