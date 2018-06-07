package com.example.nathz.theprospector;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RateUserInterest extends Activity {
    TextView honestlyTextView, howInterestedTextView, seekBarProgress, willContactTextView;
    SeekBar seekBar;
    Button nextButton;
    String regionSelected;
    Handler setText2Delay;
    AnimationSet fadeInAnimation = new AnimationSet(true);
    AnimationSet quickFadeInAnimation = new AnimationSet(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slide slide;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
            getWindow().setExitTransition(slide);
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rate_user_interest);

        nextButton = findViewById(R.id.nextButton);
        howInterestedTextView = findViewById(R.id.howInterestedTextView);
        honestlyTextView = findViewById(R.id.honestlyTextView);
        seekBar = findViewById(R.id.interestSeekBar);
        seekBarProgress = findViewById(R.id.seekBarProgress);
        willContactTextView = findViewById(R.id.willContactTextView);

        Intent region = getIntent();
        regionSelected = region.getStringExtra("regionSelected");

        setText2Delay = new Handler();

        Animation quickFadeIn = new AlphaAnimation(0,1);
        Animation fadeIn = new AlphaAnimation(0,1);
        quickFadeIn.setDuration(200);
        fadeIn.setDuration(750);
        fadeInAnimation.addAnimation(fadeIn);
        quickFadeInAnimation.addAnimation(quickFadeIn);
        howInterestedTextView.setAnimation(fadeInAnimation);
        setText2Delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                honestlyTextView.setAnimation(quickFadeInAnimation);
                honestlyTextView.setVisibility(View.VISIBLE);
            }
        }, 1250);

        seekBar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar.setMax(10);

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        seekBarProgress.setText(progress+"/"+seekBar.getMax());
                        setComment(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(getApplicationContext(), getString(R.string.to_be_contacted_toast), Toast.LENGTH_LONG).show();
                        nextButton.setAnimation(quickFadeInAnimation);
                        nextButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        seekBarProgress.setText(seekBar.getProgress()+"/"+seekBar.getMax());
                        setComment(seekBar.getProgress());
                    }
                }
        );
    }
    public void setComment(int position){
        if (position>=0 && position <4)
            willContactTextView.setText("May not be contacted.");
        if (position>=4 && position <7)
            willContactTextView.setText("Likely to be contacted.");
        if (position>=7 && position <=10)
            willContactTextView.setText("Will definitely be contacted.");
    }

    public void enterUserDetailsScreen(View view) {
        String userData[] = {regionSelected, Integer.toString(seekBar.getProgress())};
        Intent enterUserDetailsScreen = new Intent(this, CollectUserDetails.class);
        enterUserDetailsScreen.putExtra("userData",userData);
        startActivity(enterUserDetailsScreen);
    }
}