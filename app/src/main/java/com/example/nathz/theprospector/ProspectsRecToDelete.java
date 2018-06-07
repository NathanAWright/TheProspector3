package com.example.nathz.theprospector;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class ProspectsRecToDelete extends Activity {
    SwipeMenuListView deleteList;
    TextView titleExt, title;
    ArrayList<User> toDeleteArrayList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospects_rec_to_delete);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *0.9),(int) (height * 0.8));

        title = findViewById(R.id.deleteTheseTitle);
        titleExt = findViewById(R.id.titleExt);
        deleteList = findViewById(R.id.deleteList);
        Adapter adapter = new ArrayAdapter<>(this, R.layout.center_align, getRecommendedDeletes());
        deleteList.setAdapter((ListAdapter) adapter);

        // set creator
        deleteList.setMenuCreator(createSwipeMenuFormat());
        // Close Interpolator
        deleteList.setCloseInterpolator(new BounceInterpolator());
        deleteList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        showInterestLevel(position);
                        break;
                    case 1:
                        keepProspect(position);
                        break;
                }
                return false;
            }
        });
        deleteList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        deleteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showInterestLevel(position);
                return false;
            }
        });
    }

    private void keepProspect(int position) {
        toDeleteArrayList.get(position).timesContacted=2;
        startActivity(new Intent(this, ProspectsRecToDelete.class));
        finish();
    }

    public void confirmDelete(View view) {
        User.usersArrayList.removeAll(toDeleteArrayList);
        CollectUserDetails.serialiseUsers(this);
        Toast.makeText(this, "Deleting complete.", Toast.LENGTH_SHORT).show();
//        finish();
        startActivity(new Intent(this, Settings.class));
    }

    public ArrayList<String> getRecommendedDeletes(){
        ArrayList<String> recommendedToDelete = new ArrayList<>();
        for (User prospect: User.usersArrayList)
            if (prospect.timesContacted>=3){
                recommendedToDelete.add(prospect.toString());
                toDeleteArrayList.add(prospect);
            }

        if (recommendedToDelete.size()==0){
            Toast.makeText(this, "No prospects to delete.", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (recommendedToDelete.size()==1){
            title.setText(R.string.delete_prospect_query);
            title.setTextColor(Color.parseColor("#660000"));
        }else
            titleExt.setText(recommendedToDelete.size()+" prospects?");
        return recommendedToDelete;
    }

    private SwipeMenuCreator createSwipeMenuFormat() {

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem showInterestLevel = new SwipeMenuItem(
                        getApplicationContext());
                showInterestLevel.setBackground(new ColorDrawable(Color.rgb(0x77, 0xff, 0x77)));
                showInterestLevel.setWidth(150);
                showInterestLevel.setTitle(getString(R.string.see_interest_level));
                showInterestLevel.setTitleSize(15);
                showInterestLevel.setTitleColor(Color.rgb(1, 1, 3));
                SwipeMenuItem removeProspect = new SwipeMenuItem(
                        getApplicationContext());
                removeProspect.setBackground(new ColorDrawable(Color.rgb(0x00, 0x99,
                        0xFF)));
                removeProspect.setWidth(150);
                removeProspect.setTitle(getString(R.string.keep_prospect));
                removeProspect.setTitleSize(15);
                removeProspect.setTitleColor(Color.rgb(0x0, 0x0, 0x0));

                menu.addMenuItem(showInterestLevel);
                menu.addMenuItem(removeProspect);

            }
        };
        return creator;
    }
    private void showInterestLevel(int position) {

        String p = User.basicProspectInfo.get(position);
        for(User temp:User.usersArrayList)
            if (temp.toString().equals(p)) {
                Toast.makeText(this, "Interest level: "+temp.interestLevel+"/10", Toast.LENGTH_SHORT).show();
                break;
            }
    }
}
