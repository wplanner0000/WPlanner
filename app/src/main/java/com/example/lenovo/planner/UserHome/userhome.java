package com.example.lenovo.planner.UserHome;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.APIlinks;
import com.example.lenovo.planner.Location.Locationpicker;
import com.example.lenovo.planner.Nearby.MapsActivity;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.SharedPrefUserInfo;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.applicationstart.SplashScreen;
import com.example.lenovo.planner.applicationstart.signuplogintab;
import com.example.lenovo.planner.editprofile.VendorProfile;
import com.example.lenovo.planner.editprofile.profilechoose;
import com.example.lenovo.planner.profile.profile;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class userhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    UserDetails user;
    private long backPressedTime = 0;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    SharedPrefUserInfo userInfo;
    private GoogleApiClient mGoogleApiClient;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_userhome);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userInfo = SharedPrefUserInfo.getmInstance(this);
        user = new UserDetails(getApplicationContext());
        RelativeLayout relativeLayout =(RelativeLayout) findViewById(R.id.vendorlistdisplay);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle("Photographer");
        Home home =new Home();
        fragmentManager = getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.vendorlistdisplay,home);
        transaction.commit();
        View view = navigation.findViewById(R.id.navigation_home);
        view.performClick();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //fb pic on navigation drawer
        View navHeader;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(Color.WHITE);
        navigationView.setAlpha(1);


        if (user.getisVendor()==1)
        {
            sync(this,user.getUID());
        }


        navHeader = navigationView.getHeaderView(0);
        CircleImageView imageView = (CircleImageView)navHeader.findViewById(R.id.imageView);
        TextView usernamedisplay = (TextView)navHeader.findViewById(R.id.usernamedisplay);
        TextView emaildisplay = (TextView)navHeader.findViewById(R.id.emaildisplay);

        usernamedisplay.setText(user.getUserName());
        emaildisplay.setText(user.getemail());
        if (user.getisVendor()==1)
        {
            navigationView.getMenu().findItem(R.id.nav_becomeavendor).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_editprofile).setVisible(true);

        }
        if(user.getisVendor()==0)
        {
            navigationView.getMenu().findItem(R.id.nav_editprofile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_becomeavendor).setVisible(true);
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
        }
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press Back Again to Exit",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.userhome, menu);
        return true;
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
            Intent editusersss= new Intent(this, profilechoose.class);
            startActivity(editusersss);
            overridePendingTransition(R.anim.fade,R.anim.fadeout);

        } else if (id == R.id.nav_budgetcalc) {
            Intent int60 = new Intent(this , BudgetCalculator.class);
            startActivity(int60);
            overridePendingTransition(R.anim.left_in,R.anim.fadeout);
        } else if (id == R.id.nav_todolist) {

        } else if (id == R.id.nav_changepassword) {
            final EditText oldpass,newpass,confirmnewpass;
            Button cancel,savechanges;
            final AlertDialog.Builder updatepassword = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.updatepassword,null);
            updatepassword.setView(dialogView);
            cancel =(Button) dialogView.findViewById(R.id.cancel);
            savechanges =(Button) dialogView.findViewById(R.id.updatepass);
            oldpass = (EditText) dialogView.findViewById(R.id.oldpassword);
            newpass = (EditText) dialogView.findViewById(R.id.newpassword);
            confirmnewpass = (EditText) dialogView.findViewById(R.id.confirmnewpassword);
            final AlertDialog b = updatepassword.create();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    b.dismiss();
                }
            });
            savechanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String OldPassword = oldpass.getText().toString();
                    final String NewPassword = newpass.getText().toString();
                    final String ReTypePassord = confirmnewpass.getText().toString();

                    if (OldPassword.isEmpty()) {
                        oldpass.setError("Enter Old Password");
                    }else if (NewPassword.isEmpty()) {
                        newpass.setError("Enter New Password");
                    }else if (ReTypePassord.isEmpty()) {
                        confirmnewpass.setError("Enter Confirm Password");
                    }else if (!NewPassword.equals(ReTypePassord)) {
                        confirmnewpass.setError("Password Didn't Match");
                    } else {
                        final ProgressDialog progress = ProgressDialog.show(userhome.this, "Please Wait...", "Updating Password...", false, false);
                        StringRequest stringRequest;
                        stringRequest = new StringRequest(Request.Method.POST, APIlinks.passwordchange, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    progress.dismiss();
                                    Toast.makeText(userhome.this, "Password Changed Succesfully", Toast.LENGTH_SHORT).show();
                                    user.logout();
                                    Intent logouts = new Intent(userhome.this, SplashScreen.class);
                                    startActivity(logouts);
                                    overridePendingTransition(R.anim.fade, R.anim.fadeout);
                                    finish();

                                } else {
                                    progress.dismiss();
                                    Toast.makeText(userhome.this, "" + response, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("uid", user.getUID());
                                params.put("OldPassword", OldPassword);
                                params.put("NewPassword", NewPassword);
                                return params;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(stringRequest);


                    }
                }
            });
            b.setCanceledOnTouchOutside(false);
            b.show();

        } else if (id == R.id.nav_becomeavendor) {
            Intent int56 = new Intent(this , VendorProfile.class);
            startActivity(int56);
            overridePendingTransition(R.anim.left_in,R.anim.fadeout);

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
                    }
                });


            Intent logouts= new Intent(getApplicationContext(), signuplogintab.class);
            startActivity(logouts);
            overridePendingTransition(R.anim.fade,R.anim.fadeout);
            finish();



        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sync(final Context context, final String uid)
    {
        String syncurl = APIlinks.vendorsync;
        final UserDetails userDetails= new UserDetails(context);
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, syncurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        if (response.equals("fail") == false) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                userDetails.setoname(jsonObject.getString("name"));
                                userDetails.setexperience(jsonObject.getInt("experience")+"");
                                userDetails.setscontactno(jsonObject.getString("contactno"));
                                userDetails.setprice(jsonObject.getInt("price")+"");
                                userDetails.setcategory(jsonObject.getInt("category_id")+"");
                                userDetails.setcategoryint(jsonObject.getInt("category_id"));
                                userDetails.setcategoryname(jsonObject.getInt("category_id")+"");
                                userDetails.setaddress(jsonObject.getString("address"));
                                userDetails.setlongitude(jsonObject.getString("longitude"));
                                userDetails.setlatitude(jsonObject.getString("latitude"));
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }




                        }
                        else {


                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Home home =new Home();
                    fragmentManager = getSupportFragmentManager();
                    transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.vendorlistdisplay,home);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction = fragmentManager.beginTransaction();
                    todo todos = new todo();
                    transaction.setCustomAnimations(R.anim.fade,R.anim.fadeout);
                    transaction.replace(R.id.vendorlistdisplay,todos);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.navigation_getnearby:
                    Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(i);
                case R.id.navigation_favourites:
                    return true;
            }
            return false;
        }

    };
    public void result(View vi)
    {
        transaction = fragmentManager.beginTransaction();
        results result = new results();
        transaction.setCustomAnimations(R.anim.fade,R.anim.fadeout);
        transaction.replace(R.id.vendorlistdisplay,result);
        transaction.commit();
    }
    public void resultsingle(View vi)
    {
        transaction = fragmentManager.beginTransaction();
        singleshop single = new singleshop();
        transaction.setCustomAnimations(R.anim.fade,R.anim.fadeout);
        transaction.replace(R.id.vendorlistdisplay,single);
        transaction.commit();
    }

    public void setTit(String title)
    {
        toolbar.setTitle(title);
    }
}
