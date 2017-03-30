package com.example.lenovo.planner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class vendorListViewDisplay extends AppCompatActivity {

    private TextView mTextMessage;
    String txt[]=new String[]{"PHOTOGRAPHER", "MUSIC", "CATERING", "DECORATION", "VENUE", "BAKERY", "DRESSES", "GIFTSHOP", "BEAUTICIAN"};
    ListView lv;
    Integer[] imageId={R.drawable.downloadd,R.drawable.downloadd,R.drawable.downloadd,R.drawable.downloadd,R.drawable.downloadd,R.drawable.downloadd,R.drawable.downloadd,R.drawable.downloadd,R.drawable.downloadd};



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_getnearby:
                    mTextMessage.setText(R.string.title_getnearby);
                    return true;
                case R.id.navigation_favourites:
                    mTextMessage.setText(R.string.title_favourites);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list_view_display);
        lv=(ListView)findViewById(R.id.listview);
        layoutcustom adaptor=new layoutcustom(vendorListViewDisplay.this, txt,imageId);
        lv.setAdapter(adaptor);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
