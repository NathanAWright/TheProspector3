package com.example.nathz.theprospector;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LureScreen extends Activity {
    Handler setDelay;
    Runnable finalDelay, quotePart1Delay, quotePart2Delay, enticerDelay, followUp1Delay, followUp2Delay, incomeLineDelay, incomeFollowUpDelay, answerButtonsDelay;

    AnimationSet line1OutAnimation = new AnimationSet(true);
    AnimationSet line2OutAnimation = new AnimationSet(true);
    AnimationSet line3OutAnimation = new AnimationSet(true);
    AnimationSet line4OutAnimation = new AnimationSet(true);
    AnimationSet line5OutAnimation = new AnimationSet(true);
    AnimationSet line6OutAnimation = new AnimationSet(true);
//    AnimationSet line7OutAnimation = new AnimationSet(true);
//    AnimationSet line8OutAnimation = new AnimationSet(true);


    AnimationSet line1InAnimation = new AnimationSet(true);
    AnimationSet line2InAnimation = new AnimationSet(true);
    AnimationSet line3InAnimation = new AnimationSet(true);
    AnimationSet line4InAnimation = new AnimationSet(true);
    AnimationSet line5InAnimation = new AnimationSet(true);
    AnimationSet line6InAnimation = new AnimationSet(true);
    AnimationSet line7InAnimation = new AnimationSet(true);
    AnimationSet buttonInAnimation = new AnimationSet(true);
    String regionSelected;

    TextView quotePart1, quotePart2, enticer, followUp1, followUp2, incomeLine, incomeFollowUp;
    LinearLayout answerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slide slide = new Slide();
        getWindow().setExitTransition(slide);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lure_screen);


        Intent regionData = getIntent();
        regionSelected = regionData.getStringExtra("regionSelected");
        
        answerButtons = findViewById(R.id.answerButtons);
        quotePart1 = findViewById(R.id.quotePart1);
        quotePart2 = findViewById(R.id.quotePart2);
        enticer = findViewById(R.id.enticer);
        followUp1 = findViewById(R.id.followUp1);
        followUp2 = findViewById(R.id.followUp2);
        incomeLine = findViewById(R.id.incomeSourceLine);
        incomeFollowUp = findViewById(R.id.incomeSourceFollowUp);
        
        setDelay = new Handler();

        initDelays();
        Animation line1FadeOut = new AlphaAnimation(1, 0);
        Animation line2FadeOut = new AlphaAnimation(1, 0);
        Animation line3FadeOut = new AlphaAnimation(1, 0);
        Animation line4FadeOut = new AlphaAnimation(1, 0);
        Animation line5FadeOut = new AlphaAnimation(1, 0);//never used
        Animation line6FadeOut = new AlphaAnimation(1, 0);//never used

        line1FadeOut.setDuration(4000);
        line2FadeOut.setDuration(4000);
        line3FadeOut.setDuration(4600);
        line4FadeOut.setDuration(4600);
        line5FadeOut.setDuration(5000);//never used
        line6FadeOut.setDuration(7000);//never used

        Animation line1FadeIn = new AlphaAnimation(0, 1);
        Animation line2FadeIn = new AlphaAnimation(0, 1);
        Animation line3FadeIn = new AlphaAnimation(0, 1);
        Animation line4FadeIn = new AlphaAnimation(0, 1);
        Animation line5FadeIn = new AlphaAnimation(0, 1);
        Animation line6FadeIn = new AlphaAnimation(0, 1);
        Animation line7FadeIn = new AlphaAnimation(0, 1);
        Animation buttonsFadeIn = new AlphaAnimation(0, 1);


        line1FadeIn.setDuration(3000);
        line2FadeIn.setDuration(3000);
        line3FadeIn.setDuration(5000);
        line4FadeIn.setDuration(4500);
        line5FadeIn.setDuration(4500);
        line6FadeIn.setDuration(4500);
        line7FadeIn.setDuration(5000);
        buttonsFadeIn.setDuration(4500);

        line1OutAnimation.addAnimation(line1FadeOut);
        line1InAnimation.addAnimation(line1FadeIn);
        line2OutAnimation.addAnimation(line2FadeOut);
        line2InAnimation.addAnimation(line2FadeIn);
        line3OutAnimation.addAnimation(line3FadeOut);
        line3InAnimation.addAnimation(line3FadeIn);
        line4OutAnimation.addAnimation(line4FadeOut);
        line4InAnimation.addAnimation(line4FadeIn);
        line5OutAnimation.addAnimation(line5FadeOut);
        line5InAnimation.addAnimation(line5FadeIn);
        line6OutAnimation.addAnimation(line6FadeOut);
        line6InAnimation.addAnimation(line6FadeIn);
        line7InAnimation.addAnimation(line7FadeIn);
        buttonInAnimation.addAnimation(buttonsFadeIn);

        setDelay.postDelayed(quotePart1Delay, 800);
    }


    public void initDelays(){
        quotePart1Delay = new Runnable() {
            @Override
            public void run() {
                quotePart1.setAnimation(line1InAnimation);
                quotePart1.setVisibility(View.VISIBLE);
                setDelay.postDelayed(quotePart2Delay,3000);
            }
        };
        quotePart2Delay = new Runnable() {
            @Override
            public void run() {
                quotePart2.setAnimation(line2InAnimation);
                quotePart2.setVisibility(View.VISIBLE);
                setDelay.postDelayed(enticerDelay,3300);
            }
        };
        enticerDelay = new Runnable() {
            @Override
            public void run() {
                quotePart1.setAnimation(line1OutAnimation);
                quotePart2.setAnimation(line2OutAnimation);
                quotePart1.setVisibility(View.INVISIBLE);
                quotePart2.setVisibility(View.INVISIBLE);

                enticer.setAnimation(line3InAnimation);
                enticer.setVisibility(View.VISIBLE);
                setDelay.postDelayed(followUp1Delay,4600);
            }
        };
        followUp1Delay = new Runnable() {
            @Override
            public void run() {
                followUp1.setAnimation(line4InAnimation);
                followUp1.setVisibility(View.VISIBLE);

                enticer.setAnimation(line3OutAnimation);
                enticer.setVisibility(View.INVISIBLE);
                setDelay.postDelayed(followUp2Delay,4600);
            }
        };
        followUp2Delay = new Runnable() {
            @Override
            public void run() {
                followUp2.setAnimation(line5InAnimation);
                followUp2.setVisibility(View.VISIBLE);

                followUp1.setAnimation(line4OutAnimation);
                followUp1.setVisibility(View.INVISIBLE);
                setDelay.postDelayed(incomeLineDelay,5300);
            }
        };
        incomeLineDelay = new Runnable() {
            @Override
            public void run() {
                incomeLine.setAnimation(line6InAnimation);
                incomeLine.setVisibility(View.VISIBLE);

                followUp2.setAnimation(line5OutAnimation);
                followUp2.setVisibility(View.INVISIBLE);
                setDelay.postDelayed(incomeFollowUpDelay,6200);
            }
        };
        incomeFollowUpDelay = new Runnable() {
            @Override
            public void run() {
                incomeFollowUp.setAnimation(line7InAnimation);
                incomeFollowUp.setVisibility(View.VISIBLE);

                setDelay.postDelayed(answerButtonsDelay,5000);
            }
        };
        answerButtonsDelay = new Runnable() {
            @Override
            public void run() {
                answerButtons.setAnimation(buttonInAnimation);
                answerButtons.setVisibility(View.VISIBLE);
                setDelay.postDelayed(finalDelay, 5000);
            }
        };
    }


    public void rateInterestScreen(View view){
        Intent rateUserInterest = new Intent(this, RateUserInterest.class);
        rateUserInterest.putExtra("regionSelected", regionSelected);
        startActivity(rateUserInterest);
        finish();
    }

    public void goToEndOfSurveyScreen(View view){
        Intent endOfSurvey = new Intent(this, EndOfSurvey.class);
        startActivity(endOfSurvey);
    }
}
