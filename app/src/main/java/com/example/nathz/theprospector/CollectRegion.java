package com.example.nathz.theprospector;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CollectRegion extends Activity implements AdapterView.OnItemClickListener{
    ListView regionList;
    ArrayAdapter<String> regionListViewAdapter;
    String[] regions;
    TextView regionChoice;
    Button nextButton;
    Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Slide slide;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            getWindow().setExitTransition(slide);
        }
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collect_region);
        regions = new String[]{getString(R.string.tobago), getString(R.string.north), getString(R.string.south), getString(R.string.central), getString(R.string.east), getString(R.string.west), getString(R.string.international)};
        nextButton = findViewById(R.id.nextButton);
        regionList = findViewById(R.id.regionList);
        regionListViewAdapter = new ArrayAdapter<>(this, R.layout.center_align, regions);
        regionList.setAdapter(regionListViewAdapter);
        regionChoice = findViewById(R.id.regionChoice);

        regionList.setOnItemClickListener(this);

        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(300);
    }
    @Override
    public void onBackPressed() {
        if (!CollectUserDetails.isNewEntry){
            CollectUserDetails.isNewEntry=false;
            startActivity(new Intent (this, MainActivity.class));
            finish();
        }
    }
    public void startLureScreen(View view){
        if (!CollectUserDetails.isNewEntry){
            Intent lureScreen = new Intent(this, LureScreen.class);
            lureScreen.putExtra("regionSelected", regionChoice.getText().toString());
            startActivity(lureScreen);
        }else{
            Intent interestLevelScreen = new Intent(this, RateUserInterest.class);
            interestLevelScreen.putExtra("regionSelected", regionChoice.getText().toString());
            startActivity(interestLevelScreen);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        regionChoice.setText(((TextView)view).getText().toString());
        nextButton.setAnimation(fadeIn);
        nextButton.setVisibility(View.VISIBLE);
    }
}
