package com.example.lenovo.planner.editprofile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.planner.R;

public class profilechoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilechoose);
    }
    public void user(View v){
        Intent editusersss= new Intent(this, Edituser.class);
        startActivity(editusersss);
        overridePendingTransition(R.anim.fade,R.anim.fadeout);
        finish();
    }
    public void vendor(View v){
        Intent editusersss= new Intent(this, Editprofile.class);
        startActivity(editusersss);
        overridePendingTransition(R.anim.fade,R.anim.fadeout);
        finish();
    }
}
