package com.example.lenovo.planner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HP on 29-03-2017.
 */

class layoutcustom extends ArrayAdapter{
    private final Activity context;
    private final String[] txt;
    private final Integer[] imageId;
    public layoutcustom(Activity context, String[] txt, Integer[] imageId) {
        super(context, R.layout.activity_vendor_list_view_display, txt);
        this.context = context;
        this.txt = txt;
        this.imageId = imageId;
    }
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View rowView=layoutInflater.inflate(R.layout.layoutc,null,true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgg);
        txtTitle.setText(txt[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }

}
