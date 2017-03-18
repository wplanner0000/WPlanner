package com.example.lenovo.planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class newpasswordotp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpasswordotp);
    }
    public void otp(View view)
    {
        Intent in45 = new Intent(this, newpassword.class);
        startActivity(in45);
        finish();
    }
}
