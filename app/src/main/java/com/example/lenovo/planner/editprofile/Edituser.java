package com.example.lenovo.planner.editprofile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.lenovo.planner.APIlinks;
import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.example.lenovo.planner.UserHome.userhome;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edituser extends AppCompatActivity {

    EditText fname,lname,contact;
    UserDetails user;
    CircleImageView userprofile;
    String uid;
    ImageButton upload,camera;
    Button savechange;
    String url;
    private static final int CAMERA_REQUEST = 12321;
    private static final int GALLERY_REQUEST = 12121;
    GalleryPhoto galleryPhoto;
    String ImagePath = "";
    private AlertDialog alertDialog;
    public CameraPhoto cameraPhoto;
    private String ImageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        if (ActivityCompat.checkSelfPermission(Edituser.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Edituser.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Edituser.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            String[] str = {
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA,
            };
            ActivityCompat.requestPermissions(Edituser.this, str, 1);
        }

        fname =(EditText) findViewById(R.id.name);
        lname =(EditText) findViewById(R.id.lname);
        contact =(EditText) findViewById(R.id.contact);
        userprofile = (CircleImageView) findViewById(R.id.imgProfilePicture);
        upload = (ImageButton) findViewById(R.id.upload);
        camera =(ImageButton) findViewById(R.id.camera);
        savechange =(Button)findViewById(R.id.savechanges);
        galleryPhoto = new GalleryPhoto(this);
        cameraPhoto = new CameraPhoto(this);
        user = new UserDetails(getApplicationContext());
        fname.setText(user.getfirstname());
        lname.setText(user.getlastname());
        contact.setText(user.getphoneNo());
        //Picasso.with(Edituser.this).load(user.getImageUrl()).into(userprofile);
        url= user.getImageUrl();
        uid = user.getUID();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImagePath.isEmpty() || ImagePath == null) {
                    Toast.makeText(Edituser.this, "Please Choose Image to upload", Toast.LENGTH_SHORT).show();
                } else {
                    VolleyUploadImage();
                }

            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });
        Glide.with(this).load(url)
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(userprofile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(Edituser.this.getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                userprofile.setImageDrawable(circularBitmapDrawable);
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
                stringRequest = new StringRequest(Request.Method.POST, APIlinks.edituser,
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

    public void VolleyUploadImage() {

        String encodeImage = "";
        if (ImagePath != null) {
            Log.e("kdskfjkds", "abc: " + encodeImage);
            try {
                Bitmap bitmap = ImageLoader.init().from(ImagePath).requestSize(1024, 1024).getBitmap();
                encodeImage = ImageBase64.encode(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(Edituser.this, "Something wrong while encoding image photo!", Toast.LENGTH_SHORT).show();
            }
        }

        final String finalEncodeImage = encodeImage;
        final String userid = user.getUID();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIlinks.UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Database_message", response);
                        if (response.equals("success")) {
                            Toast.makeText(Edituser.this, " Image Upload successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Edituser.this, " Image Upload fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Edituser.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("imageName", ImageName);
                params.put("image", finalEncodeImage);
                params.put("id", userid);
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Edituser.this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                ImagePath = photoPath;
                File f = new File(photoPath);
                ImageName = f.getName();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    userprofile.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(Edituser.this, "Something wrong while choosing photo!", Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == CAMERA_REQUEST) {
                String photoPath = cameraPhoto.getPhotoPath();
                ImagePath = photoPath;
                File f = new File(photoPath);
                ImageName = f.getName();
                Toast.makeText(Edituser.this, "" + ImageName, Toast.LENGTH_SHORT).show();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    userprofile.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(Edituser.this, "Something wrong while loading photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}

