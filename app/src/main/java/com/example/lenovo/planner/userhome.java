package com.example.lenovo.planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.planner.SharedPreps.SharedPrefUserInfo;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.applicationstart.SplashScreen;
import com.example.lenovo.planner.editprofile.VendorProfile;
import com.example.lenovo.planner.profile.profile;
import com.squareup.picasso.Picasso;

public class userhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    UserDetails user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPrefUserInfo userInfo = SharedPrefUserInfo.getmInstance(this);
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


         Toast.makeText(this, userInfo.getUserName(), Toast.LENGTH_SHORT).show();
        usernamedisplay.setText(userInfo.getUserName());
        emaildisplay.setText(userInfo.getemail());

            Picasso.with(this).load(userInfo.getImageUrl()).into(imageView);
        navigationView.setNavigationItemSelectedListener(this);

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
            finish();
        } else if (id == R.id.nav_editprofile) {

        } else if (id == R.id.nav_budgetcalc) {

        } else if (id == R.id.nav_todolist) {

        } else if (id == R.id.nav_becomeavendor) {
            Intent int56 = new Intent(this , VendorProfile.class);
            startActivity(int56);
            overridePendingTransition(R.anim.left_in,R.anim.fadeout);
            finish();

        } else if (id == R.id.nav_logout) {
            user.logout();
            Intent logouts= new Intent(this, SplashScreen.class);
            startActivity(logouts);
            overridePendingTransition(R.anim.fade,R.anim.fadeout);
            finish();


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
