package com.example.lenovo.planner.profile;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class userprofile extends Fragment {
    CircleImageView userprofile;
    TextView name,email,phone;
    UserDetails userDetails;
    profile profiled;
    ImageButton edituser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userprofile, container, false);
        name =(TextView) view.findViewById(R.id.name);
        userprofile = (CircleImageView) view.findViewById(R.id.imgProfilePicture);
        email = (TextView) view.findViewById(R.id.email);
        phone = (TextView) view.findViewById(R.id.contact);
        edituser = (ImageButton) view.findViewById(R.id.brn_edituser);
        profiled = (profile) getActivity();
        userDetails = new UserDetails(getActivity());
        name.setText(userDetails.getUserName());
        email.setText(userDetails.getemail());
        phone.setText(userDetails.getphoneNo());
        Picasso.with(getActivity()).load(userDetails.getImageUrl()).into(userprofile);
        edituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profiled.edituserprofile(view);
            }
        });
        return view;
    }

}
