package com.example.lenovo.planner.editprofile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.UserHome.userhome;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edituser extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    EditText fname,lname,contact;
    UserDetails user;
    CircleImageView userprofile;
    String uid;
    ImageButton upload,camera;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL ="https://wplanner0000.000webhostapp.com/wplanner/upload(2).php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    Button savechange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        fname =(EditText) findViewById(R.id.name);
        lname =(EditText) findViewById(R.id.lname);
        contact =(EditText) findViewById(R.id.contact);
        userprofile = (CircleImageView) findViewById(R.id.imgProfilePicture);
        upload = (ImageButton) findViewById(R.id.upload);
        camera =(ImageButton) findViewById(R.id.camera);
        savechange =(Button)findViewById(R.id.savechanges);
        user = new UserDetails(getApplicationContext());
        fname.setText(user.getfirstname());
        lname.setText(user.getlastname());
        contact.setText(user.getphoneNo());
        uid = user.getUID();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        savechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadImage();
                final String firstname = fname.getText().toString();
                final String lastname = lname.getText().toString();
                final String contactno = contact.getText().toString();
                StringRequest stringRequest;
                final ProgressDialog loading = ProgressDialog.show(Edituser.this, "Please Wait.....", "Registering Vendor......", false, false);
                stringRequest = new StringRequest(Request.Method.POST, "https://wplanner0000.000webhostapp.com/wplanner/edituser.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("DataBase Response", response);
                                if (response.equals("success")) {
                                    loading.dismiss();
                                    user.setfirstname(firstname);
                                    user.setlastname(lastname);
                                    user.setphoneno(contactno);

                                    Intent intent2 = new Intent(Edituser.this, userhome.class);
                                    overridePendingTransition(R.anim.fade,R.anim.fadeout);
                                    startActivity(intent2);
                                } else {
                                    loading.dismiss();

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loading.dismiss();
                                Toast.makeText(Edituser.this, "error.toString", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uid",uid);
                        params.put("firstname", firstname);
                        params.put("lastname", lastname);
                        params.put("contactno", contactno);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(Edituser.this);
                requestQueue.add(stringRequest);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                userprofile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            userprofile.setImageBitmap(photo);
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void uploadImage(){
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText(Edituser.this, "success from android "+s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();

                      //  Toast.makeText(Edituser.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                user.setimage_url(image);
                //Getting Image Name
                String name = "profile_"+uid;

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("uid",uid);
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onBackPressed() {
        Button cancel,savechanges;
        final AlertDialog.Builder updatepassword = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.exit,null);
        updatepassword.setView(dialogView);
        cancel =(Button) dialogView.findViewById(R.id.cancel);
        savechanges =(Button) dialogView.findViewById(R.id.updatepass);
        final AlertDialog b = updatepassword.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(Edituser.this, userhome.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.fade,R.anim.fadeout);
                finish();
            }
        });
        b.setCanceledOnTouchOutside(false);
        b.show();
    }

}

