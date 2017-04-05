package com.example.lenovo.planner.UserHome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lenovo.planner.Adapters.CustomListAdapter;
import com.example.lenovo.planner.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    String txt[]=new String[]{"Photographer", "Music", "Catering", "Decoration", "Venue", "Bakery", "Clothing", "GiftShop", "Saloon"};
    ListView lv;
    Integer[] imageId={R.drawable.photo,R.drawable.mus,R.drawable.cate,R.drawable.ven,R.drawable.deco,R.drawable.mus,R.drawable.cate,R.drawable.ven,R.drawable.deco};


    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lv=(ListView) view.findViewById(R.id.listview);


        CustomListAdapter adapter=new CustomListAdapter(getActivity(), txt, imageId);
        lv.setAdapter(adapter);
        return view;
    }

}
