package com.example.lenovo.planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class newpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
    }
    public void home(View view)
    {
        Intent inte = new Intent(this , Login.class);
        startActivity(inte);
        finish();
    }
}
