package com.example.nathz.theprospector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CreateAdmin extends Activity{
    EditText userNameEt, password1, password2;
    Boolean p1Visible=false, p2Visible=false;
    TextView welcomeText;
    ImageView showPassword1, showPassword2;
    int passwordType;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin);
        userNameEt = findViewById(R.id.userName);
        password1 = findViewById(R.id.password);
        password2 = findViewById(R.id.verifyPassword);
        welcomeText = findViewById(R.id.welcomeText);
        showPassword1 = findViewById(R.id.showPassword1);
        showPassword2 = findViewById(R.id.showPassword2);
        passwordType=password1.getInputType();

        context = getApplicationContext();
        welcomeText.append(" new Administrator!");

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
    }

    public void checkCredentials(View view) {
        if (userNameEt.getText().toString().length()>0)
            if (password1.getText().toString().length()>0 && password1.getText().toString().equals(password2.getText().toString()))
                goToAdminView(userNameEt.getText().toString(),password1.getText().toString());
            else
                Toast.makeText(this, R.string.please_check_password, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, R.string.enter_username, Toast.LENGTH_LONG).show();
    }
    public void goToAdminView(String username, String password){
        serialiseAdmin(new Administrator(username, password), context);
        startActivity(new Intent(this, AdministratorPage.class));
        finish();
    }


    public static void serialiseAdmin(Administrator admin, Context context) {
        try{
            FileOutputStream fout= context.openFileOutput("administrator.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(admin);
            Administrator.passwordOfficial = admin.password;
            Administrator.userNameOfficial = admin.userName;
            Toast.makeText(context, "New admin info set!", Toast.LENGTH_SHORT).show();
            oos.close();
            Log.d("The Prospector", "Admin info file write successful.");
        }catch(Exception ex){
            Log.d("The Prospector", "Admin info file write not successful.");
            ex.printStackTrace();
        }
    }

}
