package com.example.lenovo.planner.applicationstart;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.APIlinks;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_signup extends Fragment implements View.OnClickListener {
    EditText et_fname, et_lname, et_email, et_mobilenumber, et_password, et_cpassword;

    Button registersignup;
   signuplogintab activity;
    UserDetails userDetails;
    public fragment_signup() {
        //
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_fragment_signup, container, false);
        et_fname = (EditText) view.findViewById(R.id.et_fname);
        et_lname = (EditText) view.findViewById(R.id.et_lname);
        et_email = (EditText) view.findViewById(R.id.et_email);
        userDetails =new UserDetails(getActivity());
        et_mobilenumber = (EditText) view.findViewById(R.id.et_mobilenumber);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_cpassword = (EditText) view.findViewById(R.id.et_cpassword);
        registersignup = (Button) view.findViewById(R.id.btn_register);
        registersignup.setOnClickListener(this);
        activity = (signuplogintab) getActivity();


        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId())
        {
            case  R.id.btn_register: {
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
                final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please Wait.....", "Registering......", false, false);
                stringRequest = new StringRequest(Request.Method.POST, APIlinks.signurl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Log.d("DataBase Response", response);
                                if (!response.equals("fail")) {
                                    loading.dismiss();
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        userDetails.setUID(jsonObject.getString("uid"));
                                        userDetails.setfirstname(firstname);
                                        userDetails.setlastname(lastname);
                                        userDetails.setemail(email);
                                        userDetails.setphoneno(phoneno);
                                        userDetails.setIsActive(true);
                                        activity.callsignup(view);
                                    }
                                    catch (JSONException e)
                                    {

                                    }

                                } else {
                                    loading.dismiss();

                                    Toast.makeText(getActivity(), "User Registration Failed...", Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error Response >>",error.getMessage());
                                loading.dismiss();
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);

            }break;
        }
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
}
