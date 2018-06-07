package com.example.nathz.theprospector;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CollectUserDetails extends Activity {
    ViewPager viewPager1, viewPager2;
    ArrayList<String> userData = new ArrayList<>();
    String hints[];
    String contactPreference[];
    TextView mainContact, secondaryContact;
    EditText primaryContactDataField, secondaryContactDataField, firstNameField, lastNameField;
    Button finishButton, newEntryButton;
    ImageButton homeButton;
    LinearLayout submitButtons;
    String []intentData={""};
    int primaryPreference=0, secondaryPreference=0;
    static Context context;
    static boolean isNewEntry=false, tryNewEntry=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            getWindow().setExitTransition(slide);
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_collect_user_details);

        hints = new String[]{getString(R.string.phone_number), getString(R.string.whatsapp_number), getString(R.string.instagram_username), getString(R.string.email_address)};
        contactPreference = new String[]{getString(R.string.nothing_selected), getString(R.string.phone_call), getString(R.string.whatsapp), getString(R.string.instagram), getString(R.string.email)};

        context = getApplicationContext();
        mainContact = findViewById(R.id.mainContact);
        secondaryContact = findViewById(R.id.secondaryContact);
        primaryContactDataField = findViewById(R.id.primaryContactDataField);
        secondaryContactDataField = findViewById(R.id.secondaryContactDataField);
        finishButton = findViewById(R.id.finishButton);
        homeButton = findViewById(R.id.homeButton);
        newEntryButton = findViewById(R.id.newEntry);
        viewPager1 = findViewById(R.id.viewPager);
        viewPager2 = findViewById(R.id.viewPager2);
        submitButtons = findViewById(R.id.submitButtons);
        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);


        if (isNewEntry)
            homeButton.setClickable(true);
        else
            homeButton.setClickable(false);
        isNewEntry=false;

        ImageAdapter imageAdapter = new ImageAdapter(this);
        viewPager1.setAdapter(imageAdapter);
        viewPager2.setAdapter(imageAdapter);
        Toast.makeText(this, R.string.swipe_for_more, Toast.LENGTH_SHORT).show();

        Intent userDataIntent = getIntent();
        intentData = userDataIntent.getStringArrayExtra("userData");
        userData.add(intentData[0]);
        userData.add(intentData[1]);

        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int inputType=1;
                if (position==1||position==2)
                    inputType=3;
                if (position==4)
                    inputType=32;
                setContactText(mainContact, primaryContactDataField, inputType, position);
                submitButtons.setVisibility(View.VISIBLE);
                primaryPreference = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (secondaryPreference!=0)
                    checkForDifferenceInContacts();
            }
        });

        viewPager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int inputType=1;
                if (position==1||position==2)
                    inputType=3;
                if (position==4)
                    inputType=32;

                secondaryPreference = position;
                setContactText(secondaryContact, secondaryContactDataField, inputType, secondaryPreference);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                checkForDifferenceInContacts();
            }
        });
    }

    private void checkForDifferenceInContacts() {
        if (primaryPreference==secondaryPreference || secondaryPreference==0)
            secondaryContactDataField.setVisibility(View.INVISIBLE);
        else
            secondaryContactDataField.setVisibility(View.VISIBLE);
    }

    public void setContactText(TextView textView, EditText et, int inputType, int position) {
        if (position == 0){
            textView.setText("Swipe for more");
            et.setVisibility(View.INVISIBLE);
        }if (position == 1)
            textView.setText("Phone Call");
        if (position == 2)
            textView.setText("Whatsapp");
        if (position == 3)
            textView.setText("Instagram");
        if (position == 4)
            textView.setText("Email");

        if (position > 0) {
            et.setVisibility(View.VISIBLE);
            et.setHint(hints[position-1]);
            et.setInputType(inputType);
        }
    }

    public void endOfSurveyScreen(View view) {
        String secondaryContactData=" ";
        if(validateUserData()) {
            if (secondaryPreference!=0)
                secondaryContactData = secondaryContactDataField.getText().toString();
            if(secondaryContactDataField.getVisibility()==View.INVISIBLE||secondaryContactDataField.getText().toString().length()==0)
                secondaryPreference=0;
            User prospect = new User(intentData[0], firstNameField.getText().toString(), lastNameField.getText().toString(), Integer.valueOf(intentData[1]), contactPreference[primaryPreference], primaryContactDataField.getText().toString(), contactPreference[secondaryPreference] , secondaryContactData);
            User.usersArrayList.add(prospect);
            serialiseUsers(this);
            if (!isNewEntry){
                Intent finishScreen = new Intent(this, EndOfSurvey.class);
                startActivity(finishScreen);
                finish();
            }
        }
    }

    private boolean validateUserData() {
        if (firstNameField.getText()!=null && lastNameField.getText()!=null && !firstNameField.getText().toString().isEmpty() && !lastNameField.getText().toString().isEmpty()){
            if (primaryContactDataField.getText()!=null && primaryPreference!=-1 && !primaryContactDataField.getText().toString().isEmpty()){
                return true;
            }else if(!tryNewEntry)
                Toast.makeText(this, primaryContactDataField.getHint().toString()+" is missing!", Toast.LENGTH_SHORT).show();
        }else if (!tryNewEntry)
            Toast.makeText(this, R.string.request_name, Toast.LENGTH_SHORT).show();
        return false;
    }

    public static void serialiseUsers(Context context){
        try{
            FileOutputStream fout= context.openFileOutput("prospects.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(User.usersArrayList);
            oos.flush();
            System.out.println("Finished writing prospect object to file 'prospects.ser'");
            Log.d("The Prospector", "Prospect list file write successful.");
            oos.close();
        }catch(Exception ex){
            Log.d("The Prospector", "Prospect list file write NOT successful.");
            ex.printStackTrace();
        }
    }

    public void returnToMainScreen(View view) {
        isNewEntry=false;
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void startNewEntry(View view) {
        tryNewEntry=true;
        if (!validateUserData()){
            Toast.makeText(this, "Please finish this entry first.", Toast.LENGTH_SHORT).show();
            tryNewEntry = false;
        }else{
            isNewEntry=true;
            tryNewEntry=false;
            endOfSurveyScreen(finishButton);//finish button is just a place holder
            startActivity(new Intent(this, CollectRegion.class));
            finish();
        }
    }
}



