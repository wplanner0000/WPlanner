package com.example.lenovo.planner.editprofile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.APIlinks;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.UserHome.userhome;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VendorProfile extends AppCompatActivity {
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST=1;

    EditText oname,contact,experiences,prices,citys,districts,states,pincodes,statuss,address,longitude,latitude;
    Spinner spn_category;
    UserDetails user;
    String uid;
    Button setaddress;
    int categoryin;
    ArrayAdapter<String> categoryadapter;
    String category_id;
    ArrayList<String> categories;
    ImageButton addprofileimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        setContentView(R.layout.activity_vendor_profile);
        setaddress =(Button) findViewById(R.id.btn_address);
        setaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                //builder.setLatLngBounds(bounds);
                try {
                    Intent intent=builder.build(VendorProfile.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                }
                catch (GooglePlayServicesRepairableException e){
                    e.printStackTrace();
                }catch (GooglePlayServicesNotAvailableException e){
                    e.printStackTrace();
                }



            }
        });

        oname = (EditText) findViewById(R.id.oname);
        contact = (EditText) findViewById(R.id.contact);
        experiences = (EditText) findViewById(R.id.experience);
        prices = (EditText) findViewById(R.id.price);
        address = (EditText) findViewById(R.id.et_address);
        longitude = (EditText) findViewById(R.id.longitude);
        latitude =(EditText) findViewById(R.id.latitude);
        spn_category =(Spinner) findViewById(R.id.spn_category);
        user = new UserDetails(getApplicationContext());
        uid = user.getUID();
        categories = new ArrayList<>();
        categories.add(0,"Select Category");
        categories.add(1,"Photographer");
        categories.add(2,"Music");
        categories.add(3,"Catering");
        categories.add(4,"Decoration");
        categories.add(5,"Venue");
        categories.add(6,"Bakery");
        categories.add(7,"Clothing");
        categories.add(8,"Gift Shop");
        categories.add(9,"Saloon");

        categoryadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        spn_category.setAdapter(categoryadapter);
        spn_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category_id = spn_category.getSelectedItemId()+"";
                categoryin = spn_category.getSelectedItemPosition();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
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
        final String experience = experiences.getText().toString();
        final String addresss = address.getText().toString();
        final String longitudes = longitude.getText().toString();
        final String latitudes = latitude.getText().toString();


        StringRequest stringRequest;
        final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "Registering Vendor......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, APIlinks.vendorprofile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        if (response.equals("success")) {
                            loading.dismiss();
                            user.setoname(name);
                            user.setscontactno(contactno);
                            user.setexperience(experience);
                            user.setprice(price);
                            user.setaddress(addresss);
                            user.setlongitude(longitudes);
                            user.setlatitude(latitudes);
                            user.setcategoryname(category_id);
                            user.setcategory(category_id);
                            user.setisVendor(1);
                            Intent intent2 = new Intent(VendorProfile.this, userhome.class);
                            overridePendingTransition(R.anim.fade,R.anim.fadeout);
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
                params.put("experience", experience);
                params.put("address", addresss);
                params.put("longitude", longitudes);
                params.put("latitude", latitudes);
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
        String fullexperience = experiences.getText().toString();
        String fulladdress = address.getText().toString();
        String fulllongitude = longitude.getText().toString();
        String fulllatitude = latitude.getText().toString();

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
        if (fulladdress.isEmpty()) {
            address.setError("Enter Address");
            return true;
        }
        if (fulllongitude.isEmpty()) {
            longitude.setError("Enter Longitude");
            return true;
        }
        if (fulllatitude.isEmpty()) {
            latitude.setError("Enter Latitude");
            return true;
        }
        if (category_id.equals(""))
        {
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"This app requires location permissin to be granted",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PLACE_PICKER_REQUEST){
            if (resultCode==RESULT_OK){
                Place place=PlacePicker.getPlace(data, VendorProfile.this);
                address.setText(place.getAddress());
                longitude.setText(place.getLatLng().latitude+"");
                latitude.setText(place.getLatLng().longitude+"");

            }
        }
    }
    @Override
    public void onBackPressed() {
        Button cancel,savechanges;
        final AlertDialog.Builder updatepassword = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.exit,null);
        updatepassword.setView(dialogView);
        cancel =(Button) dialogView.findViewById(R.id.cancel);
        savechanges =(Button) dialogView.findViewById(R.id.updatepass);
        final AlertDialog b = updatepassword.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(VendorProfile.this, userhome.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.fade,R.anim.fadeout);
                finish();
            }
        });
        b.setCanceledOnTouchOutside(false);
        b.show();
    }


}
