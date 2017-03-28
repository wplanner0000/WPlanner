package com.example.lenovo.planner.SharedPreps;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lenovo.planner.R;

/**
 * Created by Rusty on 3/27/2017.
 */

public class SharedPrefUserInfo
{
    private static final String FACEBOOK_PREFS = "FBPrefs" ;

    private static SharedPrefUserInfo mInstance;
    private static Context mCtx;

    private SharedPrefUserInfo(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefUserInfo getmInstance(Context context) {
        if(mInstance == null) {
            mInstance = new SharedPrefUserInfo(context);
        }
        return mInstance;
    }

    public boolean saveUserInfo(String firstName, String lastName, String email,String imageurl){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("image_url",imageurl);
        editor.putString("email",email);
        editor.apply();
        return true;
    }


    public String getImageUrl() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("image_url","https://wplanner0000.000webhostapp.com/wplanner/account_logo.jpg");
    }
    public String getUserName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("firstName", "firstName") +" "+sharedPreferences.getString("lastName", "lastName");
    }
    public String getemail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(FACEBOOK_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "email");
    }

}
