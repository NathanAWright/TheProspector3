package com.example.nathz.theprospector;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends Activity {
    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
    }

    public void validateUserCredentials(View view){
        if (username.getText().toString().length()==0)
            Toast.makeText(this, "Enter a username!", Toast.LENGTH_SHORT).show();
        else if (password.getText().toString().length()==0)
            Toast.makeText(this, "Enter a password!", Toast.LENGTH_SHORT).show();
        else if (!(password.getText().toString().compareTo(Administrator.passwordOfficial)==0)||!(username.getText().toString().compareToIgnoreCase(Administrator.userNameOfficial)==0))
            Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
        else{
            startActivity(new Intent (this, AdministratorPage.class));
            finish();
        }
    }

}
