package com.example.lenovo.planner.forgotpassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.R;

import java.util.HashMap;
import java.util.Map;

public class forgotpassword extends AppCompatActivity {
    EditText et_forgotphone,et_forgotemail;
    String forgote = "https://wplanner0000.000webhostapp.com/wplanner/emailauthentication.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        et_forgotemail = (EditText) findViewById(R.id.et_forgotemail);
        et_forgotphone = (EditText) findViewById(R.id.et_forgotphone);
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
    public void proceed(View view)
    {
        if(inValid())
        {
            return;
        }
        final String email = et_forgotemail.getText().toString();
        final String phoneno = et_forgotphone.getText().toString();


        StringRequest stringRequest;
        final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "Matching Email And Phone Number......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, forgote,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        if (response.equals("success")) {
                            loading.dismiss();
                            Intent intent20 = new Intent(forgotpassword.this, newpasswordotp.class);
                            startActivity(intent20);
                            finish();

                        } else {
                            loading.dismiss();
                            et_forgotemail.setError("Invalid Email or Mobile Number");
                            et_forgotphone.setError("Invalid Email or Mobile Number");

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(forgotpassword.this, "error.toString", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("phoneno", phoneno);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public boolean inValid() {
        String getforgotemail = et_forgotemail.getText().toString();
        String getforgotphone = et_forgotphone.getText().toString();


        if (getforgotemail.isEmpty() && getforgotphone.isEmpty())
        {
        et_forgotemail.setError("Enter Email Or Phone Number");
            return true;
        }

        return false;
    }
}
