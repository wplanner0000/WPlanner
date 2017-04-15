package com.example.lenovo.planner.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class vendorprofileview extends Fragment {
    CircleImageView profileimage;
    TextView oname,contact,category,experience,price,city,state,district,pincode,status,address,longitude,latitude;
    UserDetails userDetails;
    ImageButton editvendor;
    profile profiledd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userDetails = new UserDetails(getActivity());
        View view = inflater.inflate(R.layout.fragment_vendorprofileview, container, false);
        profileimage =(CircleImageView) view.findViewById(R.id.imgProfilePicture);
        oname = (TextView) view.findViewById(R.id.oname);
        category = (TextView) view.findViewById(R.id.spn_category);
        contact = (TextView) view.findViewById(R.id.contact);
        experience = (TextView) view.findViewById(R.id.experience);
        price = (TextView) view.findViewById(R.id.price);
        address = (TextView) view.findViewById(R.id.et_address);
        longitude = (TextView) view.findViewById(R.id.longitude);
        latitude = (TextView) view.findViewById(R.id.latitude);
        status = (TextView) view.findViewById(R.id.status);
        editvendor = (ImageButton) view.findViewById(R.id.brn_editvendor);
        profiledd = (profile) getActivity();
        Picasso.with(getActivity()).load(userDetails.getImageUrl()).into(profileimage);
        category.setText(userDetails.getcategoryname());
        oname.setText(userDetails.getoname());
        contact.setText(userDetails.getscontactno());
        experience.setText(userDetails.getexperience());
        price.setText(userDetails.getprice());
        address.setText(userDetails.getaddress());
        longitude.setText(userDetails.getlongitude());
        latitude.setText(userDetails.getlatitude());
        status.setText(userDetails.getstatus());
        editvendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profiledd.editvendorprofile(view);
            }
        });
        return view;
    }

}
