package com.example.lenovo.planner.applicationstart;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.planner.Adapters.profileadapter;
import com.example.lenovo.planner.Adapters.signuploginAdapter;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.UserHome.userhome;
import com.example.lenovo.planner.forgotpassword.forgotpassword;

public class signuplogintab extends AppCompatActivity {

    private long backPressedTime = 0;
    private static final String TWITTER_KEY = "KTrL1XbIuXI1umX7pFKiSyFh1";
    private static final String TWITTER_SECRET = "kXzxPsjT8JYRMuwCCKJpsk5npszppui6cLQYWThHB3a22o44XC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuplogintab);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("SignUp"));
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final signuploginAdapter adapter = new signuploginAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }
    @Override
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
    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press Back Again to Exit",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();

        }

    }

    public void forgottransition(View vi)
    {
        Intent forgot = new Intent(this, forgotpassword.class);
        startActivity(forgot);
        overridePendingTransition(R.anim.left_in,R.anim.right_in);


    }
    public void callsignup(View vi)
    {
        Intent in = new Intent(this, userhome.class);
        startActivity(in);
        overridePendingTransition(R.anim.left_in,R.anim.right_in);
        finish();
    }
    public void logincall(View vi)
    {
        Intent intent2 = new Intent(this, userhome.class);
        startActivity(intent2);
        overridePendingTransition(R.anim.left_in,R.anim.right_in);
        finish();
    }

}

