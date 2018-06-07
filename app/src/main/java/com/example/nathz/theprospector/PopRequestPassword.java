package com.example.nathz.theprospector;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PopRequestPassword extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_request_password);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *0.55),(int) (height * 0.2));
    }

    public void validateAdminPassword(View view) {
        EditText passwordEntered = findViewById(R.id.passwordEntered);
        if (passwordEntered.getText()!=null)
            if (passwordEntered.getText().toString().compareTo(Administrator.passwordOfficial)==0)
                startActivity(new Intent(this, ChangeAdminInfo.class));
            else
                Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_SHORT).show();

            finish();
    }
}
