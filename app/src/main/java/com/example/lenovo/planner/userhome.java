package com.example.lenovo.planner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.SharedPreps.SharedPrefUserInfo;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.applicationstart.SplashScreen;
import com.example.lenovo.planner.editprofile.Editprofile;
import com.example.lenovo.planner.editprofile.VendorProfile;
import com.example.lenovo.planner.profile.profile;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class userhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    UserDetails user;
    SharedPrefUserInfo userInfo;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_userhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userInfo = SharedPrefUserInfo.getmInstance(this);
        user = new UserDetails(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //fb pic on navigation drawer
        View navHeader;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView)navHeader.findViewById(R.id.imageView);
        TextView usernamedisplay = (TextView)navHeader.findViewById(R.id.usernamedisplay);
        TextView emaildisplay = (TextView)navHeader.findViewById(R.id.emaildisplay);


      //   Toast.makeText(this, userInfo.getUserName(), Toast.LENGTH_SHORT).show();
        usernamedisplay.setText(user.getUserName());
        emaildisplay.setText(user.getemail());
        if (user.getisVendor()==1)
        {
            navigationView.getMenu().findItem(R.id.nav_becomeavendor).setVisible(false);

        }
        else
        {
            navigationView.getMenu().findItem(R.id.nav_editprofile).setVisible(false);
        }

            Picasso.with(this).load(user.getImageUrl()).into(imageView);
            navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.userhome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            Intent int57 = new Intent(this, profile.class);
            startActivity(int57);
            overridePendingTransition(R.anim.left_in,R.anim.fadeout);
        } else if (id == R.id.nav_editprofile) {
            Intent int56 = new Intent(this , Editprofile.class);
            startActivity(int56);
            overridePendingTransition(R.anim.left_in,R.anim.fadeout);
            finish();


        } else if (id == R.id.nav_budgetcalc) {

        } else if (id == R.id.nav_todolist) {

        } else if (id == R.id.nav_changepassword) {
            changepassword();

        } else if (id == R.id.nav_becomeavendor) {
            Intent int56 = new Intent(this , VendorProfile.class);
            startActivity(int56);
            overridePendingTransition(R.anim.left_in,R.anim.fadeout);
            finish();

        } else if (id == R.id.nav_locate) {
            Intent int56 = new Intent(this , Locationpicker.class);
            startActivity(int56);
            overridePendingTransition(R.anim.left_in,R.anim.fadeout);

        } else if (id == R.id.nav_logout) {
            user.logout();
            LoginManager.getInstance().logOut();


                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Toast.makeText(userhome.this, "Please Login to Continue", Toast.LENGTH_SHORT).show();
                    }
                });


            Intent logouts= new Intent(this, SplashScreen.class);
            startActivity(logouts);
            overridePendingTransition(R.anim.fade,R.anim.fadeout);
            finish();
                            // userInfo.logoutfbgb();


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sync(final Context context, final String uid)
    {
        String syncurl ="https://wplanner0000.000webhostapp.com/wplanner/vendorsync.php";
        final UserDetails userDetails= new UserDetails(context);
        StringRequest stringRequest;
        //final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "Verifying......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, syncurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        if (response.equals("fail") == false) {
          //                  loading.dismiss();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                userDetails.setoname(jsonObject.getString("name"));
                                userDetails.setexperience(jsonObject.getInt("experience")+"");
                                userDetails.setscontactno(jsonObject.getString("contactno"));
                                userDetails.setprice(jsonObject.getInt("price")+"");
                                userDetails.setcategory(jsonObject.getInt("category_id")+"");
                                userDetails.setcity(jsonObject.getString("city"));
                                userDetails.setdistrict(jsonObject.getString("district"));
                                userDetails.setstate(jsonObject.getString("state"));
                                userDetails.setpincode(jsonObject.getInt("pincode")+"");
                                userDetails.setstatus(jsonObject.getString("status"));
                //                Toast.makeText(context, "Sync", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }




                        }
                        else {
            //                loading.dismiss();

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
              //          loading.dismiss();
                        Toast.makeText(context, "error.toString", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid",uid);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    private void changepassword()
    {
        Button cancel ;
        final AlertDialog.Builder updatepassword = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatepassword,null);
        updatepassword.setView(dialogView);
        cancel =(Button) dialogView.findViewById(R.id.cancel);
        final AlertDialog b = updatepassword.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
        b.setCanceledOnTouchOutside(false);
        b.show();
    }


}
