package com.example.nathz.theprospector;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.baoyz.swipemenulistview.SwipeMenu;
//import com.baoyz.swipemenulistview.SwipeMenuCreator;
//import com.baoyz.swipemenulistview.SwipeMenuItem;
//import com.baoyz.swipemenulistview.SwipeMenuListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.Serializable;
import java.security.cert.CollectionCertStoreParameters;

public class AdministratorPage extends Activity implements Serializable {
    public String userName, password;
    SwipeMenuListView prospectList;
//    ListView prospectList;
    ListAdapter adapter;
    TextView prospectCount;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            getWindow().setExitTransition(slide);
        }
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_administrator_page);
        prospectList = findViewById(R.id.prospectList);
        prospectCount = findViewById(R.id.prospectCount);
        adapter = new ArrayAdapter<>(this, R.layout.center_align, User.basicProspectInfo);

        Administrator.loggedIn=true;

        prospectList.setAdapter(adapter);
        prospectCount.setText(getProspectCount());

        // set creator
        prospectList.setMenuCreator(createSwipeMenuFormat());
        // Close Interpolator
        prospectList.setCloseInterpolator(new BounceInterpolator());
        prospectList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        showDetails(position);
                        break;
                    case 1:
                        markAsContacted(position);
                        break;
                    case 2:
                        deleteProspect(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        prospectList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        prospectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showRecommendation(position);
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Log out to return to main menu.", Toast.LENGTH_SHORT).show();
    }
    private void showRecommendation(int position) {

        String p = User.basicProspectInfo.get(position);
        for(User temp:User.usersArrayList)
            if (temp.toString().equals(p)) {
                if (temp.timesContacted==0)
                    temp.setRecommendation(getResources().getString(R.string.contact_soon));
                if (temp.timesContacted > 0)
                    temp.setRecommendation(getResources().getString(R.string.give_second_chance));
                if (temp.timesContacted > 1)
                    temp.setRecommendation(getResources().getString(R.string.make_final_offer));
                if (temp.timesContacted > 2)
                    temp.setRecommendation(getResources().getString(R.string.delete_prospect));
                Toast.makeText(this, temp.recommendation, Toast.LENGTH_SHORT).show();
                break;
            }
    }

    private void showDetails(int position) {
        Intent i = new Intent(this, ProspectDetails.class);
        i.putExtra("position", position);
        startActivity(i);
    }

    private void deleteProspect(int position) {
        Intent i = new Intent(this, DeleteProspect.class);
        i.putExtra("position", position);
        startActivity(i);
//        finish();
    }

    private void markAsContacted(int position) {
        String p = User.basicProspectInfo.get(position);
        User user=User.usersArrayList.get(0);
        for(User temp:User.usersArrayList){
            if (temp.toString().equals(p)){
                temp.timesContacted++;
                user=temp;
            }
        }

        if (user.timesContacted>1)
            Toast.makeText(this, String.format("%s %s %d %s", user.firstName, getString(R.string._contacted), user.timesContacted, getString(R.string.times)), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, String.format("%s %s", user.firstName, getString(R.string._contacted)), Toast.LENGTH_SHORT).show();
        CollectUserDetails.serialiseUsers(this);
    }

    private SwipeMenuCreator createSwipeMenuFormat() {

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem markContactedItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                markContactedItem.setBackground(new ColorDrawable(Color.rgb(0x77, 0xff, 0x77)));
                // set item width
                markContactedItem.setWidth(150);
                // set item title
                markContactedItem.setTitle(getString(R.string.mark_as_contacted));
                // set item title fontsize
                markContactedItem.setTitleSize(15);
                // set item title font color
                markContactedItem.setTitleColor(Color.rgb(1, 1, 3));
                // add to menu

                SwipeMenuItem infoItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background0099cc
                infoItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x99,
                        0xFF)));
                // set item width
                infoItem.setWidth(150);
                // set item title
                infoItem.setTitle(getString(R.string.info));
                // set item title fontsize
                infoItem.setTitleSize(15);
                // set item title font color
                infoItem.setTitleColor(Color.rgb(0x0, 0x0, 0x0));
                // add to menu

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xDc, 0x77,
                        0x77)));
                // set item width
                deleteItem.setWidth(150);
                // set item title
                deleteItem.setTitle(getString(R.string.delete));
                // set item title fontsize
                deleteItem.setTitleSize(15);
                // set item title font color
                deleteItem.setTitleColor(Color.rgb(0x0, 0x0, 0x0));
                // add to menu

                menu.addMenuItem(infoItem);
                menu.addMenuItem(markContactedItem);
                menu.addMenuItem(deleteItem);

            }
        };
        return creator;
    }

    public void logOut(View view) {
        Administrator.loggedIn=false;

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public String getProspectCount(){
        int count = User.basicProspectInfo.size();
        String s;
        if (count==0)
            s= "No Prospects yet.";
        else if (count==1)
            s= count +" Prospect so far.";
        else if (count<8)
            s= count+" Prospects so far!";
        else s= count+" Prospects!";

        if (Settings.all)
            return s;
        else if (Settings.timesContacted || Settings.interestLevel)
            return s+" (Sorted)";
        else
            return s+" (Filtered)";
    }

    public void settingsPage(View view) {
        startActivity(new Intent (this, Settings.class));
    }

}