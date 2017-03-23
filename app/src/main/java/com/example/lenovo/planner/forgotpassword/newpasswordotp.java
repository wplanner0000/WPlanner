package com.example.lenovo.planner.forgotpassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.planner.R;
import com.example.lenovo.planner.forgotpassword.newpassword;

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
