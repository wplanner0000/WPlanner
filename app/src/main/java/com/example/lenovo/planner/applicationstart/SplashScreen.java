package com.example.lenovo.planner.applicationstart;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.planner.R;
import com.example.lenovo.planner.forgotpassword.forgotpassword;

public class SplashScreen extends AppCompatActivity {

    Animation animation,fade,translateLogin,translateLogo,fadeout;
    ImageView view;
    TextView textView;
    Typeface typeface;
    Animation left_in,right_in;
    CardView layout;
    RelativeLayout relativeLayout;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);

        translateLogin= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translateforlogin);

        translateLogo= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translatelogo);

        fade= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        fadeout= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
        layout=(CardView) findViewById(R.id.frag_holder);
        left_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left_in);
        right_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_in);
        relativeLayout =(RelativeLayout) findViewById(R.id.frag_holder2);

        login_fragment fragment= new login_fragment();

        fragmentManager = getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.frag_holder2,fragment);
        transaction.commit();

        textView = (TextView) findViewById(R.id.appName);

        view= (ImageView) findViewById(R.id.logo);

        view.startAnimation(animation);
        view.bringToFront();
        typeface = Typeface.createFromAsset(getAssets(),"GreatVibes-Regular.ttf");
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                textView.startAnimation(fade);
                textView.setVisibility(View.VISIBLE);
                //  Toast.makeText(getApplicationContext(), "chal gya", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        textView.startAnimation(fadeout);
                        textView.setVisibility(View.GONE);

                        view.startAnimation(translateLogo);
                    layout.startAnimation(translateLogin);
                        layout.setVisibility(View.VISIBLE);
                       layout.bringToFront();

                    }
                },1900);

            }
        },1200);
    }
    public void callregister(View vi)
    {
        transaction = fragmentManager.beginTransaction();
        fragment_signup signups = new fragment_signup();
        transaction.setCustomAnimations(R.anim.fade,R.anim.fadeout);
        transaction.replace(R.id.frag_holder2,signups);
        transaction.commit();
    }
    public void calllogin(View vi)
    {
        transaction = fragmentManager.beginTransaction();
        login_fragment logins = new login_fragment();
        transaction.setCustomAnimations(R.anim.fade,R.anim.fadeout);
        transaction.replace(R.id.frag_holder2,logins);
        transaction.commit();
    }
    public void forgottransition(View vi)
    {
        Intent forgot = new Intent(this, forgotpassword.class);
        startActivity(forgot);
        overridePendingTransition(R.anim.left_in,R.anim.right_in);


    }


}
