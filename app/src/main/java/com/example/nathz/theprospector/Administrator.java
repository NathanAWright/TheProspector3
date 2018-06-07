package com.example.nathz.theprospector;

import android.app.Application;
import java.io.Serializable;

public class Administrator extends Application implements Serializable {
    public static boolean loggedIn=false, adminCreated=false;
    public static String userNameOfficial="", passwordOfficial="";
    public String userName, password;
//    public static Context context;

    Administrator(String userName, String password){
        this.userName=userName;
        this.password=password;
    }
}
