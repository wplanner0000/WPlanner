package com.example.lenovo.planner.SharedPreps;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LENOVO on 3/24/2017.
 */

public class UserDetails {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    static UserDetails classObject;




    public UserDetails(Context context)
    {
        this.context= context;
        sharedPreferences = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        editor.apply();
    }


    public static synchronized UserDetails getObject(Context context)
    {
        if(classObject!=null)
        {
            return classObject= new UserDetails(context);
        }


        return classObject;
    }

    public void setIsActive(boolean isActive)
    {
        editor.putBoolean("status",isActive);
        editor.apply();
    }

    public boolean getIsActive()
    {
        return sharedPreferences.getBoolean("status",false);
    }

    public void setisVendor(boolean isvendor)
    {
        editor.putBoolean("isVendor",isvendor);
    }

    public boolean getisVendor()
    {
        return sharedPreferences.getBoolean("isVendor",false);
    }

    public void setUID(int UID)
    {
        editor.putInt("UID",UID);
        editor.apply();
    }

    public int getUID()
    {
        return sharedPreferences.getInt("UID",0);
    }

    public void setemail(String email)
    {
        editor.putString("email",email);
        editor.apply();
    }

    public String getemail()
    {
        return sharedPreferences.getString("email","null");
    }

    public void setphoneno(int phoneno)
    {
        editor.putInt("phoneno",phoneno);
        editor.apply();
    }

    public int getphoneNo()
    {
        return sharedPreferences.getInt("phoneno",0);
    }
    public void setfname(String fname)
    {
        editor.putString("fname",fname);
        editor.apply();
    }

    public String getfname()
    {
        return sharedPreferences.getString("fname","null");
    }
    public void setlname(String lname)
    {
        editor.putString("lname",lname);
        editor.apply();
    }

    public String getname()
    {
        return sharedPreferences.getString("name","null");
    }

    public void logout()
    {
        editor.clear();
        editor.apply();
    }

}
