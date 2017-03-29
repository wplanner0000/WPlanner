package com.example.lenovo.planner.applicationstart;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.lenovo.planner.Locationpicker;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.SharedPrefUserInfo;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.userhome;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class login_fragment extends Fragment implements View.OnClickListener
{

    EditText et_email,et_password;
    Button frgtPassword,login,register;
    String loginurl =  "https://wplanner0000.000webhostapp.com/wplanner/logtry.php";
    SplashScreen activity;
    UserDetails userDetails;
    //facebook
    SignInButton gpButton;
    LoginButton fbButton;

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    private static final int RC_SIGN_IN = 0511;
    private static final int FB_SIGN_IN = 1996;
    private GoogleApiClient signInApi;

    public login_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        // Inflate the layout for this fragment

        final View view= inflater.inflate(R.layout.fragment_login_fragment, container, false);
        userDetails = new UserDetails(getActivity());

        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);

        frgtPassword=(Button) view.findViewById(R.id.btn_forgotpassword);
        frgtPassword.setOnClickListener(this);

        register=(Button) view.findViewById(R.id.btn_registerhere);
        register.setOnClickListener(this);

        login=(Button) view.findViewById(R.id.btn_login);
        login.setOnClickListener(this);

        activity = (SplashScreen) getActivity();
        fbButton = (LoginButton) view.findViewById(R.id.fbButton);
        fbButton.setFragment(this);
        gpButton = (SignInButton) view.findViewById(R.id.gpButton);
      //  btn_forgotpassword= (Button) view. findViewById(R.id.btn_forgotpassword);
        gpButton.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        fbButton.setReadPermissions(Arrays.asList("public_profile, email, user_friends," +
                "user_location,user_hometown"));

        fbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try{

                                    String email = object.getString("email");
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String fname = object.getString("first_name");
                                    String lname = object.getString("last_name");
                                    String gender = object.getString("gender");
                                    String imageUrl = "https://graph.facebook.com/" + id + "/picture?type=large";
                                    userDetails.setIsActive(true);
                                    userDetails.setfirstname(fname);
                                    userDetails.setlastname(lname);
                                    userDetails.setemail(email);
                                    userDetails.setimage_url(imageUrl);
                                    SharedPrefUserInfo.getmInstance(getActivity()).saveUserInfo(fname,lname,email,imageUrl);
                                    activity.logincall(view);






                                }

                                catch (Exception ex){
                                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday, first_name, last_name, location, work, education, age_range, timezone, hometown");
                request.setParameters(parameters);
                request.executeAsync();

                //Access Token to manage Logout
                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                        if(currentAccessToken == null) {

                        }
                    }
                };
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //end of facebook integration
        //start of google

        //start of google+ login
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gpButton.setSize(SignInButton.SIZE_STANDARD);
        gpButton.setScopes(signInOptions.getScopeArray());

        signInApi = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                       // Toast.makeText(activity, "Connection "+connectionResult, Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
        //end of google+ login
       return view;

}

   private void GoogleSignIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(signInApi);
        startActivityForResult(intent,RC_SIGN_IN);
    }


    private void handleSignInResult(GoogleSignInResult result) {
      //  Toast.makeText(getActivity(), result.isSuccess()+"", Toast.LENGTH_SHORT).show();
        if(result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            String first_name = account.getGivenName();
            String last_name = account.getFamilyName();
            String profileImage = account.getPhotoUrl().toString();
            String email = account.getEmail();
            String gid = account.getId();
            String token = account.getIdToken();
            userDetails.setIsActive(true);
            userDetails.setfirstname(first_name);
            userDetails.setlastname(last_name);
            userDetails.setemail(email);
            userDetails.setimage_url(profileImage);
            Toast.makeText(getActivity(), "successfully login", Toast.LENGTH_SHORT).show();
            //redirect user to next page
            SharedPrefUserInfo.getmInstance(getActivity()).saveUserInfo(first_name,last_name,email,profileImage);
            activity.logincall(getView());
            //startActivity(new Intent(getApplicationContext(), userhome.class));




        }
    }
        public void onActivityResult(int requestCode,int resultCode,Intent data)
        {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode == RC_SIGN_IN) {
          //  Toast.makeText(getActivity(), "onActivity", Toast.LENGTH_SHORT).show();
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(googleSignInResult);
        }
        else
            callbackManager.onActivityResult(requestCode, resultCode, data);
         }

    @Override
   public void onStart() {
        super.onStart();

       OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(signInApi);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
      }
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
    public void onClick(final View view) {
        switch(view.getId())
        {
            case R.id.btn_forgotpassword :
            {

               activity.forgottransition(view);

            }
            break;
            case R.id.gpButton :
                GoogleSignIn();
                break;
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
                                if (response.equals("fail") == false) {
                                    loading.dismiss();
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        userDetails.logout();
                                        userDetails.setUID(jsonObject.getInt("uid")+"");
                                        userDetails.setemail(jsonObject.getString("email"));
                                        userDetails.setIsActive(true);
                                        userDetails.setisVendor(jsonObject.getInt("usertype"));
                                        userDetails.setfirstname(jsonObject.getString("first_name"));
                                        userDetails.setlastname(jsonObject.getString("last_name"));
                                        userDetails.setphoneno(jsonObject.getString("phoneno"));
                                        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        activity.logincall(view);
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                    }




                                }
                                else {
                                    loading.dismiss();
                                    et_password.setError("Invalid Username or Password");
                                }
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
