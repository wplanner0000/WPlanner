package com.example.lenovo.planner.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.planner.R;
import com.example.lenovo.planner.SharedPreps.UserDetails;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class userprofile extends Fragment {
    ImageView userprofile;
    TextView name,email,phone;
    UserDetails userDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_userprofile, container, false);
        name =(TextView) view.findViewById(R.id.name);
        userprofile = (ImageView) view.findViewById(R.id.imgProfilePicture);
        email = (TextView) view.findViewById(R.id.email);
        phone = (TextView) view.findViewById(R.id.contact);
        userDetails = new UserDetails(getActivity());
        name.setText(userDetails.getUserName());
        email.setText(userDetails.getemail());
        phone.setText(userDetails.getphoneNo());
        Picasso.with(getActivity()).load(userDetails.getImageUrl()).into(userprofile);
        return view;
    }

}
