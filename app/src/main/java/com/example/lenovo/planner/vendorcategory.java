package com.example.lenovo.planner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class vendorcategory extends AppCompatActivity {
    RadioGroup groupcategory;
    RadioButton photographer, music, catering, decoration, weddinghall, bakery, clothing, giftshop, beautician;
    String categoryurl = "https://wplanner0000.000webhostapp.com/wplanner/vendorcategory.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int item[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        setContentView(R.layout.activity_vendorcategory);
        groupcategory = (RadioGroup) findViewById(R.id.groupcategory);
        groupcategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                // Toast.makeText(MainActivity.this,"Radio Button Selected",Toast.LENGTH_SHORT).show();
            }
        });
        photographer = (RadioButton) findViewById(R.id.photographer);
        music = (RadioButton) findViewById(R.id.music);
        catering = (RadioButton) findViewById(R.id.catering);
        decoration = (RadioButton) findViewById(R.id.decoration);
        weddinghall = (RadioButton) findViewById(R.id.weddinghall);
        bakery = (RadioButton) findViewById(R.id.bakery);
        clothing = (RadioButton) findViewById(R.id.clothing);
        giftshop = (RadioButton) findViewById(R.id.giftshop);
        beautician = (RadioButton) findViewById(R.id.beautician);
        photographer.setId(item[0] - 48);
        music.setId(item[1] - 48);
        catering.setId(item[2] - 48);
        decoration.setId(item[3] - 48);
        weddinghall.setId(item[4] - 48);
        bakery.setId(item[5] - 48);
        clothing.setId(item[6] - 48);
        giftshop.setId(item[7] - 48);
        beautician.setId(item[8] - 48);
    }

    public void next(View view) {
        if (groupcategory.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "No Selected Radio Button", Toast.LENGTH_SHORT).show();
            // no radio buttons are checked
        } else {
            final String uid = "5";

            final int selectedid = groupcategory.getCheckedRadioButtonId();
            final String category_id = "" + selectedid;
            //SharedPreferences pref = getSharedPreferences("MyPref", 0);
            //SharedPreferences.Editor editor = pref.edit();
            //editor.putString("category_id",category_id);
            //editor.putString("uid",uid);
            //editor.commit();

            StringRequest stringRequest;
            final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "Registering......", false, false);
            stringRequest = new StringRequest(Request.Method.POST, categoryurl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("DataBase Response", response);
                            if (response.equals("success")) {
                                loading.dismiss();
                                Intent intent = new Intent(getApplicationContext(), VendorProfile.class);
                                //intent.putExtra("uid",uid);
                                //intent.putExtra("category_id",category_id);
                                startActivity(intent);
                            } else {
                                loading.dismiss();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(vendorcategory.this, "error.toString", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uid", uid);
                    params.put("category_id", category_id);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

}
