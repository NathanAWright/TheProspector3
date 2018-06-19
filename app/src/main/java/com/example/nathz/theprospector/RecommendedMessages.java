package com.example.nathz.theprospector;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendedMessages extends FragmentActivity {
    ViewPager viewPager;
    TextView title, yesAnswer1, noAnswer1, yesAnswer2, noAnswer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_messages);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *0.9),(int) (height * 0.8));

        viewPager = findViewById(R.id.viewPager);
        RecMsgSwipeAdapter swipeAdapter = new RecMsgSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(swipeAdapter);

    }

    public void swipeRightMessage(View view) {
        Toast.makeText(this, "Swipe Right!", Toast.LENGTH_SHORT).show();
    }

    public void swipeLeftMessage(View view) {
        Toast.makeText(this, "Swipe Left!", Toast.LENGTH_SHORT).show();
    }
}
