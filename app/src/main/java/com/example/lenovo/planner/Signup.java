package com.example.lenovo.planner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText et_fname, et_lname, et_email, et_mobilenumber, et_password, et_cpassword;
    String signupurl = "https://wplanner0000.000webhostapp.com/wplanner/signtry.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_fname = (EditText) findViewById(R.id.et_fname);
        et_lname = (EditText) findViewById(R.id.et_lname);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobilenumber = (EditText) findViewById(R.id.et_mobilenumber);
        et_password = (EditText) findViewById(R.id.et_password);
        et_cpassword = (EditText) findViewById(R.id.et_cpassword);
    }
    public void register(View view)
    {
        if (inValid())
        {
            return;
        }
        final String firstname = et_fname.getText().toString();
        final String lastname = et_lname.getText().toString();
        final String email = et_email.getText().toString();
        final String phoneno = et_mobilenumber.getText().toString();
        final String password = et_password.getText().toString();

        StringRequest stringRequest;
        final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "Registering......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, signupurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("DataBase Response", response);
                        if (response.equals("success")) {
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), "User Registration Success....", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(Signup.this, Locationpicker.class);
                            startActivity(in);
                            finish();
                        } else {
                            loading.dismiss();
                            Toast.makeText(Signup.this, "User Registration Failed...", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error Response >>",error.getMessage());
                        loading.dismiss();
                        Toast.makeText(Signup.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("email", email);
                params.put("phoneno",phoneno);
                params.put("password", password);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean inValid() {
        String getfullname =  et_fname.getText().toString();
        String getname = et_lname.getText().toString();
        String getemail = et_email.getText().toString();
        String getphoneno = et_mobilenumber.getText().toString();
        String getpassword = et_password .getText().toString();
        String getcpassword = et_cpassword .getText().toString();
        if (getfullname.isEmpty()) {
            et_fname.setError("Enter First Name");
            return true;
        }
        if (getname.isEmpty()) {
            et_lname.setError("Enter Last Name");
            return true;
        }
        if (getpassword.isEmpty()) {
            et_password.setError("Enter Password");
            return true;
        }
        if (getcpassword.isEmpty()) {
            et_cpassword.setError("Enter Confirm Password");
            return true;
        }
        if (getemail.isEmpty()) {
            et_email.setError("Enter Email");
            return true;
        }
        if (getphoneno.isEmpty()) {
            et_mobilenumber.setError("Enter Mobile Number");
            return true;
        }
        if(!getpassword.equals(getcpassword))
        {
            et_cpassword.setError("Password Doesn't Match");
            return true;
        }

        return false;
    }
    public void loginback(View view)
    {
        Intent inten4 = new Intent(this, Login.class);
        startActivity(inten4);
        finish();
    }

}
