package com.example.nathz.theprospector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends Activity {
    TextView mpCountDown1, mpCountDown2, mpCountDown3, mpCountDownMp;
    Handler setDelay;
    Runnable startDelay2, startDelay1, startDelayMp, startDelayButtonDisplay;
    FrameLayout mainActivityLayout;
    ImageView logo;
    AnimationSet fadeInAnimation = new AnimationSet(true);
    AnimationSet fadeIn2Animation = new AnimationSet(true);
    AnimationSet fadeOutAnimation = new AnimationSet(true);
    Button surveyStartButton, adminViewButton;
    Administrator admin;
    static String phone, email, whatsapp, instagram;
    static String north, south, east, west, international, central, tobago;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            getWindow().setExitTransition(explode);
        }
        north = getString(R.string.north);
        south = getString(R.string.south);
        east = getString(R.string.east);
        west = getString(R.string.west);
        tobago = getString(R.string.tobago);
        international = getString(R.string.international);
        central = getString(R.string.central);

        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_main);
        setDelay = new Handler();
        surveyStartButton = findViewById(R.id.surveyStartButton);
        adminViewButton = findViewById(R.id.adminViewButton);
        logo = findViewById(R.id.majorpipslogo);
        mainActivityLayout = findViewById(R.id.mainActivityLayout);
        mpCountDown3 = findViewById(R.id.mpCountDown3);
        mpCountDown2 = findViewById(R.id.mpCountDown2);
        mpCountDown1 = findViewById(R.id.mpCountDown1);
        mpCountDownMp = findViewById(R.id.mpCountDownMp);
        context = getApplicationContext();
        phone = getString(R.string.phone_call);
        email = getString(R.string.email);
        whatsapp = getString(R.string.whatsapp);
        instagram = getString(R.string.instagram);

        User.usersArrayList.clear();
        User.usersArrayList.addAll(deserialiseProspects(context));
        initDelays();
        setDelay.postDelayed(startDelay2, 600);
        setDelay.postDelayed(startDelayButtonDisplay, 500);

        Animation fadeIn = new AlphaAnimation(0, 1), fadeOut = new AlphaAnimation(1, 0), fadeIn2 = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        fadeOut.setDuration(1500);
        fadeIn2.setDuration(4500);
        fadeInAnimation.addAnimation(fadeIn);
        fadeIn2Animation.addAnimation(fadeIn2);
        logo.setAnimation(fadeInAnimation);
        fadeOutAnimation.addAnimation(fadeOut);

        surveyStartButton.setAnimation(fadeIn2Animation);
        adminViewButton.setAnimation(fadeIn2Animation);
    }

    public void initDelays() {
        startDelay2 = new Runnable() {
            @Override
            public void run() {
                mpCountDown3.setVisibility(View.INVISIBLE);
                mpCountDown2.setVisibility(View.VISIBLE);
                setDelay.postDelayed(startDelay1, 500);
            }
        };
        startDelay1 = new Runnable() {
            @Override
            public void run() {
                mpCountDown2.setVisibility(View.INVISIBLE);
                mpCountDown1.setVisibility(View.VISIBLE);
                setDelay.postDelayed(startDelayMp, 500);
            }
        };
        startDelayMp = new Runnable() {
            @Override
            public void run() {
                mpCountDown1.setVisibility(View.INVISIBLE);
                mpCountDownMp.setVisibility(View.VISIBLE);
                mpCountDownMp.setAnimation(fadeOutAnimation);
                mpCountDownMp.setVisibility(View.INVISIBLE);
            }
        };
    }

    public void surveyStart(View view) {
        Intent surveyScreen = new Intent(this, CollectRegion.class);
        startActivity(surveyScreen);
    }

    public void deserialiseAdmin() {
        try {
            FileInputStream fin = getApplicationContext().openFileInput("administrator.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            admin = (Administrator) ois.readObject();
            ois.close();
            Administrator.userNameOfficial = admin.userName;
            Administrator.passwordOfficial = admin.password;
            Administrator.adminCreated = true;
        } catch (Exception ex) {
            Administrator.adminCreated = false;
            ex.printStackTrace();
        }
    }

    public void checkForAdminFile(View view) {
        deserialiseAdmin();
        getBasicProspectInfo();
        Intent adminView;
        if (!Administrator.adminCreated) {
            adminView = new Intent(this, CreateAdmin.class);
        } else {
            adminView = new Intent(this, AdminLogin.class);
        }
        startActivity(adminView);
    }

    public static ArrayList<User> deserialiseProspects(Context context) {
        ArrayList<User> users = new ArrayList<>();
        try {
            FileInputStream fin = context.openFileInput("prospects.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            users = (ArrayList<User>) ois.readObject();
            int id = -1;
            for (User p : users)
                if (id < p.id)
                    id = p.id;
            User.idCounter = id;
            MainActivity.getBasicProspectInfo();
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return users;
        }
        return users;
    }

    public static void getBasicProspectInfo() {
        ArrayList<String> info = new ArrayList<>();
        for (User p : User.usersArrayList) {
            if (Settings.all)
                info.add(p.toString());
            else {
                if (Settings.region) {
                    if (Settings.north && p.region.equals(north)|| Settings.central && p.region.equals(central)|| Settings.south && p.region.equals(south) || Settings.west && p.region.equals(west) || Settings.east && p.region.equals(east) || Settings.international && p.region.equals(international) || Settings.tobago && p.region.equals(tobago))
                        info.add(p.toString());
                } else if (Settings.interestLevel) {
                    info.addAll(interestLevelSort());
                    Toast.makeText(context,"Sorted.", Toast.LENGTH_SHORT).show();
                    break;
                }else if (Settings.timesContacted) {
                    info.addAll(timesContactedSort());
                    Toast.makeText(context,"Sorted.", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    if (Settings.emailOnly && p.primaryContactPreference.equals(email)) {
                        info.add(p.toString());
                    } else if (Settings.phoneCallOnly && p.primaryContactPreference.equals(phone)) {
                        info.add(p.toString());
                    } else if (Settings.whatsappOnly && p.primaryContactPreference.equals(whatsapp)) {
                        info.add(p.toString());
                    } else if (Settings.instagramOnly && p.primaryContactPreference.equals(instagram)) {
                        info.add(p.toString());
                    }
                }
            }
        }
        User.basicProspectInfo.clear();
        User.basicProspectInfo.addAll(info);
    }
    public static ArrayList<String> interestLevelSort(){
        ArrayList<String> infoStrings = new ArrayList<>();
        ArrayList<User> userInfo = new ArrayList<>();
        ArrayList<User> tempUserInfoList = new ArrayList<>();
        User temp=User.usersArrayList.get(0);//placeholder
        Boolean marker;

        for (User p:User.usersArrayList){
            marker=false;
            if (userInfo.size()==0)
                userInfo.add(p);
            else{
                for (User pInUserInfoList:userInfo){
                    if (p.interestLevel>pInUserInfoList.interestLevel){
                        temp = pInUserInfoList;
                        marker = true;
                        break;
                    }
                }
                if (marker){
                    for (User pBeforeMarkedPoint:userInfo){
                        if (pBeforeMarkedPoint.toString().equals(temp.toString())){
                            tempUserInfoList.add(p);
                            tempUserInfoList.add(temp);
                        }else{
                            tempUserInfoList.add(pBeforeMarkedPoint);
                        }
                    }
                    userInfo.clear();
                    userInfo.addAll(tempUserInfoList);
                    tempUserInfoList.clear();
                }else
                    userInfo.add(p);
            }
        }
        for (User user:userInfo)
            infoStrings.add(user.toString());
        return infoStrings;
    }

    public static ArrayList<String> timesContactedSort(){
        ArrayList<String> infoStrings = new ArrayList<>();
        ArrayList<User> userInfo = new ArrayList<>();
        ArrayList<User> tempUserInfoList = new ArrayList<>();
        User temp=User.usersArrayList.get(0);//placeholder
        Boolean marker;

        for (User p:User.usersArrayList){
            marker=false;
            if (userInfo.size()==0)
                userInfo.add(p);
            else{
                for (User pInUserInfoList:userInfo){
                    if (p.timesContacted>pInUserInfoList.timesContacted){
                        temp = pInUserInfoList;
                        marker = true;
                        break;
                    }
                }
                if (marker){
                    for (User pBeforeMarkedPoint:userInfo){
                        if (pBeforeMarkedPoint.toString().equals(temp.toString())){
                            tempUserInfoList.add(p);
                            tempUserInfoList.add(temp);
                        }else{
                            tempUserInfoList.add(pBeforeMarkedPoint);
                        }
                    }
                    userInfo.clear();
                    userInfo.addAll(tempUserInfoList);
                    tempUserInfoList.clear();
                }else
                    userInfo.add(p);
            }
        }
        for (User user:userInfo)
            infoStrings.add(user.toString());
        return infoStrings;
    }
}
