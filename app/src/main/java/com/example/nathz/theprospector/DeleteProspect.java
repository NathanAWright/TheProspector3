package com.example.nathz.theprospector;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteProspect extends Activity {
    int pos=-1;
    TextView textView;
    User p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *0.7),(int) (height * 0.5));

        setContentView(R.layout.activity_delete_prospect);
        Intent i = getIntent();
        pos=i.getIntExtra("position", 0);
        textView = findViewById(R.id.prospectInfo);

        String pString= User.basicProspectInfo.get(pos), prospect="";
        for (User pTemp: User.usersArrayList){
            if (pTemp.toString().equals(pString)){
                prospect+=pTemp.firstName+" "+pTemp.lastName;
                p=pTemp;
                break;
            }
        }
        textView.setText(prospect);
    }

    public void finishActivity(View view) {
        startActivity(new Intent(this,AdministratorPage.class));
        finish();
    }

    public void deleteProspect(View view) {
        User.usersArrayList.remove(p);
        CollectUserDetails.serialiseUsers(this);
        MainActivity.getBasicProspectInfo();
        startActivity(new Intent(this, AdministratorPage.class));
        finish();
    }
}
