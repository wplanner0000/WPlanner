package com.example.lenovo.planner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText et_email,et_password;

    String loginurl =  "https://wplanner0000.000webhostapp.com/wplanner/logtry.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);


    }
    public void forgotpassword(View view)
    {
        Intent forgot = new Intent(this, forgotpassword.class);
        startActivity(forgot);

    }
    public void registerhere(View view)
    {
        Intent reg = new Intent(this, Signup.class);
        startActivity(reg);
        finish();
    }
    public void login(View view)
    {
        if (inValid())
        {
            return;
        }
        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();


        StringRequest stringRequest;
        final ProgressDialog loading = ProgressDialog.show(this, "Please Wait.....", "Verifying......", false, false);
        stringRequest = new StringRequest(Request.Method.POST, loginurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DataBase Response", response);
                        try {
                            if (response.equals("success")) {
                                loading.dismiss();
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                Toast.makeText(Login.this, "Name - " + jsonObject1.getString("name"), Toast.LENGTH_LONG).show();
                                Intent intent2 = new Intent(getApplicationContext(), Locationpicker.class);
                                startActivity(intent2);
                                finish();

                            }
                            else{
                                loading.dismiss();

                            }
                        } catch (JSONException e)
                        {

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Login.this, "error.toString", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("password", password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public boolean inValid()
    {
        String fullemail = et_email.getText().toString();
        String fullpassword = et_password.getText().toString();

        if (fullemail.isEmpty()) {
            et_email.setError("Enter Email");
            return true;
        }
        if (fullpassword.isEmpty()) {
            et_password.setError("Enter Password");
            return true;
        }
        return false;
    }
}
