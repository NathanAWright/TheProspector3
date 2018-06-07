package com.example.nathz.theprospector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProspectDetails extends Activity {
    TextView recommendationTv, nameField, primaryContactData, secondaryContactData, prospectIdTextView, region, primContactPreference, secContactPreference, interestLevel;
    EditText numberEditText;
    User globalP;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospect_details);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *0.9),(int) (height * 0.8));
        Intent intent = getIntent();

        interestLevel = findViewById(R.id.interestLevel);
        numberEditText = findViewById(R.id.contactedEditText);
        nameField = findViewById(R.id.title);
        primaryContactData = findViewById(R.id.primaryContactInfo);
        secondaryContactData = findViewById(R.id.secondaryContactInfo);
        prospectIdTextView = findViewById(R.id.idTextView);
        region = findViewById(R.id.regionTextView);
        primContactPreference = findViewById(R.id.primaryContactPreference);
        secContactPreference = findViewById(R.id.secondaryContactPreference);
        recommendationTv = findViewById(R.id.recommendation);

        String p = User.basicProspectInfo.get(intent.getIntExtra("position", 0));

        for(User temp:User.usersArrayList)
            if (temp.toString().equals(p)){
                globalP = temp;
                prospectIdTextView.append(Integer.toString(temp.id));
                recommendationTv.setText(getThisRecommendation(temp.timesContacted));
                numberEditText.setText(Integer.toString(temp.timesContacted));
                numberEditText.setHint(Integer.toString(temp.timesContacted));
                nameField.setText(temp.firstName+" "+temp.lastName);
                region.setText(" "+temp.region);
                primContactPreference.append(temp.primaryContactPreference+" ");
                primaryContactData.append("("+temp.primContactData+")");
                secContactPreference.setText(temp.secondaryContactPreference);
                secondaryContactData.append("("+temp.secContactData+")");
                interestLevel.setText(" "+Integer.toString(temp.interestLevel));
                break;
            }

        TextWatcher filterTextWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        numberEditText.addTextChangedListener(filterTextWatcher);
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CollectUserDetails.serialiseUsers(getApplicationContext());
//                Toast.makeText(getApplicationContext(), "Saved.", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private String getThisRecommendation(int timesContacted) {
        if (timesContacted==0)
            return(getResources().getString(R.string.contact_soon));
        if (timesContacted == 1)
            return(getResources().getString(R.string.give_second_chance));
        if (timesContacted == 2)
            return(getResources().getString(R.string.make_final_offer));
        if (timesContacted > 2)
            return(getResources().getString(R.string.delete_prospect));
        return(getResources().getString(R.string.contact_soon));
    }

    public void saveContactAmount(View view) {
        globalP.timesContacted = Integer.valueOf(numberEditText.getText().toString());
        CollectUserDetails.serialiseUsers(this);
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
        finish();
    }
}
