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
        editor.putBoolean("isactive",isActive);
        editor.apply();
    }

    public boolean getIsActive()
    {
        return sharedPreferences.getBoolean("isactive",false);
    }

    public void setisVendor(int isvendor)
    {
        editor.putInt("isVendor",isvendor);
    }

    public int getisVendor()
    {
        return sharedPreferences.getInt("isVendor",0);
    }

  /*  public void setrandom(int random)
    {
        editor.putInt("random",random);
    }

    public int getrandom()
    {
        return sharedPreferences.getInt("random",0);
    }
*/
    public void setUID(String UID)
    {
        editor.putString("UID",UID);
        editor.apply();
    }

    public String getUID()
    {
        return sharedPreferences.getString("UID","Null");
    }
    public void setitem(String item)
    {
        editor.putString("item",item);
        editor.apply();
    }

    public String getitem()
    {
        return sharedPreferences.getString("item","Null");
    }
    public void setitem1(long item1)
    {
        editor.putLong("item1",item1);
        editor.apply();
    }

    public long getitem1()
    {
        return sharedPreferences.getLong("item1",0);
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

    public void setphoneno(String phoneno)
    {
        editor.putString("phoneno",phoneno);
        editor.apply();
    }

    public String getphoneNo()
    {
        return sharedPreferences.getString("phoneno","Not Available");
    }
    public void setfirstname(String firstname)
    {
        editor.putString("firstname",firstname);
        editor.apply();
    }

    public String getfirstname()
    {
        return sharedPreferences.getString("firstname","null");
    }
    public void setlastname(String lastname)
    {
        editor.putString("lastname",lastname);
        editor.apply();
    }

    public String getlastname()
    {
        return sharedPreferences.getString("lastname","null");
    }

    public void setoname(String oname)
    {
        editor.putString("oname",oname);
        editor.apply();
    }

    public String getoname()
    {
        return sharedPreferences.getString("oname","null");
    }

    public void setimage_url(String image_url)
    {
        editor.putString("image_url",image_url);
        editor.apply();
    }

    public String getImageUrl()
    {
        return sharedPreferences.getString("image_url","https://wplanner0000.000webhostapp.com/wplanner/account_logo.jpg");
    }

    public String getUserName() {
        return sharedPreferences.getString("firstname", "firstname") +" "+sharedPreferences.getString("lastname", "lastname");
    }

    public void setscontactno(String scontactno)
    {
        editor.putString("scontactno",scontactno);
        editor.apply();
    }

    public String getscontactno()
    {
        return sharedPreferences.getString("scontactno","Not Available");
    }

    public void setexperience(String experience)
    {
        editor.putString("experience",experience);
        editor.apply();
    }

    public String getexperience()
    {
        return sharedPreferences.getString("experience","Null");
    }

    public void setprice(String price)
    {
        editor.putString("price",price);
        editor.apply();
    }

    public String getprice()
    {
        return sharedPreferences.getString("price","Null");
    }

    public void setstate(String state)
    {
        editor.putString("state",state);
        editor.apply();
    }

    public String getstate()
    {
        return sharedPreferences.getString("state","null");
    }

    public void setcity(String city)
    {
        editor.putString("city",city);
        editor.apply();
    }

    public String getcity()
    {
        return sharedPreferences.getString("city","null");
    }
    public void setaddress(String address)
    {
        editor.putString("address",address);
        editor.apply();
    }

    public String getaddress()
    {
        return sharedPreferences.getString("address","null");
    }

    public void setlongitude(String longitude)
    {
        editor.putString("longitude",longitude);
        editor.apply();
    }

    public String getlongitude()
    {
        return sharedPreferences.getString("longitude","null");
    }
    public void setlatitude(String latitude)
    {
        editor.putString("latitude",latitude);
        editor.apply();
    }

    public String getlatitude()
    {
        return sharedPreferences.getString("latitude","null");
    }



    public void setdistrict(String district)
    {
        editor.putString("district",district);
        editor.apply();
    }

    public String getdistrict()
    {
        return sharedPreferences.getString("district","null");
    }


    public void setpincode(String pincode)
    {
        editor.putString("pincode",pincode);
        editor.apply();
    }

    public String getpincode()
    {
        return sharedPreferences.getString("pincode","Null");
    }

    public void setstatus(String status)
    {
        editor.putString("status",status);
        editor.apply();
    }

    public String getstatus()
    {
        return sharedPreferences.getString("status","null");
    }


    public void setcategoryname(String category)
    {
        if(category.equals("1")) {
            editor.putString("categoryname", "Photographer");
        }
        if(category.equals("2")) {
            editor.putString("categoryname", "Music");
        }
        if(category.equals("3")) {
            editor.putString("categoryname", "Catering");
        }
        if(category.equals("4")) {
            editor.putString("categoryname", "Decoration");
        }
        if(category.equals("5")) {
            editor.putString("categoryname", "Venue");
        }
        if(category.equals("6")) {
            editor.putString("categoryname", "Bakery");
        }
        if(category.equals("7")) {
            editor.putString("categoryname", "Clothing");
        }
        if(category.equals("8")) {
            editor.putString("categoryname", "Gift Shop");
        }
        if(category.equals("9")) {
            editor.putString("categoryname", "Saloon");
        }

        editor.apply();
    }

    public String getcategoryname()
    {
        return sharedPreferences.getString("categoryname","null");
    }

    public void setcategory(String category)
    {
        editor.putString("category",category);
        editor.apply();
    }

    public String getcategory()
    {
        return sharedPreferences.getString("category","null");
    }

    public void setcategoryint(int categoryint)
    {
        editor.putInt("categoryint",categoryint);
        editor.apply();
    }

    public int getcategoryint()
    {
        return sharedPreferences.getInt("categoryint",0);
    }


    public void logout()
    {
        editor.clear();
        editor.apply();
    }

}
