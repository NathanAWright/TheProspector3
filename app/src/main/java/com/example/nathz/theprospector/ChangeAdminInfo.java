package com.example.nathz.theprospector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeAdminInfo extends Activity {
    EditText userNameEt, password1, password2;
    Boolean p1Visible=false, p2Visible=false;
    TextView welcomeText;
    ImageView showPassword1, showPassword2;
    int passwordType;
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_admin_info);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width *0.8),(int) (height * 0.8));

        userNameEt = findViewById(R.id.userName);
        password1 = findViewById(R.id.password);
        password2 = findViewById(R.id.verifyPassword);
        welcomeText = findViewById(R.id.welcomeText);
        showPassword1 = findViewById(R.id.showPassword1);
        showPassword2 = findViewById(R.id.showPassword2);
        passwordType=password1.getInputType();
        context = getApplicationContext();
        Toast.makeText(this, getString(R.string.to_keep_username), Toast.LENGTH_LONG).show();

        showPassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p1Visible){
                    password1.setInputType(144);
                    p1Visible=false;
                    showPassword1.setImageResource(0);
                    showPassword1.setImageResource(R.drawable.viewpassword);
                }else{
                    password1.setInputType(129);
                    p1Visible=true;
                    showPassword1.setImageResource(0);
                    showPassword1.setImageResource(R.drawable.hidepassword);
                }
            }
        });
        showPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p2Visible){
                    password2.setInputType(144);
                    p2Visible=false;
                    showPassword2.setImageResource(0);
                    showPassword2.setImageResource(R.drawable.viewpassword);
                }else{
                    password2.setInputType(129);
                    p2Visible=true;
                    showPassword2.setImageResource(0);
                    showPassword2.setImageResource(R.drawable.hidepassword);
                }
            }
        });

        welcomeText.append(" "+ Administrator.userNameOfficial+"!");
    }


    public void goToAdminView(String username, String password){
        CreateAdmin.serialiseAdmin(new Administrator(username, password), getApplicationContext());
        finish();
    }
    public void checkCredentials(View view) {
        String username;
        if (userNameEt.getText().toString().length()>0)
            username=userNameEt.getText().toString();
        else
            username=Administrator.userNameOfficial;
        if (password1.getText().toString().length()>0 && password1.getText().toString().equals(password2.getText().toString()))
            goToAdminView(username,password1.getText().toString());
        else
            Toast.makeText(this, R.string.please_check_password, Toast.LENGTH_SHORT).show();
    }
}
