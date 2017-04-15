package com.example.lenovo.planner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.planner.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LENOVO on 4/14/2017.
 */

public class cateringAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> shopname;
    ArrayList<String> imageurl;
    ArrayList<String> shopaddress;
    LayoutInflater layoutInflater;

    public cateringAdapter(Activity context, ArrayList<String> shopname, ArrayList<String> shopaddress, ArrayList<String> imageurl) {
        this.context = context;
        this.shopname = shopname;
        this.shopaddress = shopaddress;
        this.imageurl = imageurl;
    }

    @Override
    public int getCount() {
        return this.shopname.size();
    }

    @Override
    public Object getItem(int position) {
        return this.shopname.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shops,parent,false);
        TextView shop = (TextView) convertView.findViewById(R.id.shopname);
        TextView shopaddres = (TextView) convertView.findViewById(R.id.shopaddress);
        CircleImageView image = (CircleImageView) convertView.findViewById(R.id.profileimage);

        shop.setText(shopname.get(position));
        shopaddres.setText(shopaddress.get(position));
        Picasso.with(context).load(imageurl.get(position)).into(image);

        return convertView;
    }
}
