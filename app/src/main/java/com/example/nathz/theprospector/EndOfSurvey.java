package com.example.nathz.theprospector;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.transition.Slide;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.Toast;

public class EndOfSurvey extends Activity {
    AnimationSet animationSet1 = new AnimationSet(true);
    Animation restartButtonAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            getWindow().setExitTransition(slide);
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_end_of_survey);

        Handler setDelay = new Handler();

        restartButtonAnimation = new AlphaAnimation(0,1);
        restartButtonAnimation.setDuration(600);

        animationSet1.addAnimation(restartButtonAnimation);
        setDelay.postDelayed(new Runnable() {
            @Override
            public void run() {
                Button restartButton = findViewById(R.id.restartButton);
                restartButton.setAnimation(restartButtonAnimation);
                restartButton.setVisibility(View.VISIBLE);
            }
        }, 1000);

    }

    public void restartSurvey(View view) {
        Intent surveyStart = new Intent(this, MainActivity.class);
        startActivity(surveyStart);
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
