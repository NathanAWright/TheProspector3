package com.example.nathz.theprospector;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class Settings extends Activity {
    Spinner sortOptionsSpinner;
    Adapter adapter;
    RelativeLayout checkboxes;
    Button applyChangesBtn, recMessagesBtn;
    static boolean central=false, north=false, south=false, east=false, west=false, international=false, tobago=false, region=false, all=true, interestLevel=false, emailOnly=false, whatsappOnly=false, instagramOnly=false, phoneCallOnly=false, timesContacted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkboxes = findViewById(R.id.checkboxes);
        sortOptionsSpinner = findViewById(R.id.sortOptionsSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.prospectListSortOptions, android.R.layout.simple_spinner_item);
        sortOptionsSpinner.setAdapter((SpinnerAdapter) adapter);
        sortOptionsSpinner.setSelection(0);
        applyChangesBtn = findViewById(R.id.applyFilter);
        recMessagesBtn = findViewById(R.id.recommendedMessagesButton);

        sortOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyChangesBtn.setVisibility(View.VISIBLE);
                if (position<=1)
                    all=true;
                if (position==2)
                    interestLevel=true;
                if (position==3)
                    timesContacted=true;
                if (position==4)
                    instagramOnly=true;
                if (position==5)
                    whatsappOnly=true;
                if (position==6)
                    phoneCallOnly=true;
                if (position==7)
                    emailOnly=true;
                if (position==8) {
                    checkboxes.setVisibility(View.VISIBLE);
                    recMessagesBtn.setVisibility(View.INVISIBLE);
                    region=true;
                }else
                    checkboxes.setVisibility(View.INVISIBLE);
                if (position>3 && position<8)
                    Toast.makeText(Settings.this, "Note: Prospects are filtered by their primary contact preferences.", Toast.LENGTH_SHORT).show();
                deselectOthers(position);
                if (position!=8)
                    MainActivity.getBasicProspectInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortOptionsSpinner.setSelection(0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdministratorPage.class));
        finish();
    }
    private void deselectOthers(int position) {

        if (position!=1 && position!=0)
            all=false;
        if (position!=2)
            interestLevel=false;
        if (position!=3)
            timesContacted=false;
        if (position!=4)
            instagramOnly=false;
        if (position!=5)
            whatsappOnly=false;
        if (position!=6)
            phoneCallOnly=false;
        if (position!=7)
            emailOnly=false;
        if (position!=8){
            region=false;
            recMessagesBtn.setVisibility(View.VISIBLE);
        }
    }

    public void deleteLingeringProspects(View view) {
        startActivity(new Intent(this, ProspectsRecToDelete.class));
    }

    public void changeAdminInfo(View view) {
        startActivity(new Intent(this, PopRequestPassword.class));
    }

    public void applyProspectFilter(View view) {
        CheckBox northBox, southBox, eastBox, westBox, centralBox, tobagoBox, internationalBox;
        northBox = findViewById(R.id.northBox);
        southBox = findViewById(R.id.southBox);
        eastBox = findViewById(R.id.eastBox);
        centralBox = findViewById(R.id.centralBox);
        westBox = findViewById(R.id.westBox);
        tobagoBox = findViewById(R.id.tobagoBox);
        internationalBox = findViewById(R.id.internationalBox);

        north = northBox.isChecked();
        south = southBox.isChecked();
        east = eastBox.isChecked();
        west = westBox.isChecked();
        tobago = tobagoBox.isChecked();
        international = internationalBox.isChecked();
        central = centralBox.isChecked();

        if (!north && !south && !east && !west && !tobago && !international && !central)
            Toast.makeText(this, "Please select a region.", Toast.LENGTH_SHORT).show();
        else{
            MainActivity.getBasicProspectInfo();
            Toast.makeText(this, "Filter Applied", Toast.LENGTH_SHORT).show();
        }
    }

    public void adminPage(View view) {
        startActivity(new Intent(this, AdministratorPage.class));
        finish();
    }

    public void viewRecommendedMessages(View view) {
        startActivity(new Intent(this, RecommendedMessages.class));
    }
}
