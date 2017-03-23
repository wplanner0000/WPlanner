package com.example.lenovo.planner.applicationstart;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.Locationpicker;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.applicationstart.SplashScreen;
import com.example.lenovo.planner.forgotpassword.forgotpassword;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class login_fragment extends Fragment implements View.OnClickListener {

    EditText et_email,et_password;
    Button frgtPassword,login,register;
    String loginurl =  "https://wplanner0000.000webhostapp.com/wplanner/logtry.php";
    SplashScreen activity;
    public login_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_fragment, container, false);

        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);

        frgtPassword=(Button) view.findViewById(R.id.btn_forgotpassword);
        frgtPassword.setOnClickListener(this);

        register=(Button) view.findViewById(R.id.btn_registerhere);
        register.setOnClickListener(this);

        login=(Button) view.findViewById(R.id.btn_login);
        login.setOnClickListener(this);

        activity = (SplashScreen) getActivity();


        return view;
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

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_forgotpassword :
            {

               activity.forgottransition(view);

            }break;
            case R.id.btn_login :
            {

                if (inValid())
                {
                    return;
                }
                final String email = et_email.getText().toString();
                final String password = et_password.getText().toString();


                StringRequest stringRequest;
                final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please Wait.....", "Verifying......", false, false);
                stringRequest = new StringRequest(Request.Method.POST, loginurl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("DataBase Response", response);
                                //try {
                                if (response.equals("success")) {
                                    loading.dismiss();
                                    //JSONObject jsonObject = new JSONObject(response);
                                    //JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    //JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    //Toast.makeText(Login.this, "Name - " + jsonObject1.getString("name"), Toast.LENGTH_LONG).show();
                                    Intent intent2 = new Intent(getActivity(), Locationpicker.class);
                                    startActivity(intent2);
                                    getActivity().finish();

                                }
                                else{
                                    loading.dismiss();

                                }
                                //} catch (JSONException e)
//                        {

                                //                      }
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.dismiss();
                                Toast.makeText(getActivity(), "error.toString", Toast.LENGTH_SHORT).show();
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

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);

            }break;
            case R.id.btn_registerhere :
            {
                activity.callregister(view);
            }break;
        }
    }
}
