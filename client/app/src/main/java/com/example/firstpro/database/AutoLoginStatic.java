package com.example.firstpro.database;

import android.content.Context;
import android.content.SharedPreferences;

public class AutoLoginStatic {

    private String password="";
    private String UserNum="";
    private boolean Teacher = true;
    private SharedPreferences loginInfo = null;

    private static AutoLoginStatic autoLoginStatic = new AutoLoginStatic();

    public static AutoLoginStatic getInstance(){
        return autoLoginStatic;
    }

    public String getUserNum(Context context){

        if(loginInfo == null){
            loginInfo = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        }

        UserNum = loginInfo.getString("usernum","");
        return UserNum;
    }

    public String getPassword(Context context){
        if(loginInfo == null){
            loginInfo = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        }

        password = loginInfo.getString("password","");
        return password;

    }

    public boolean isTeacher(Context context){
        if(loginInfo == null){
            loginInfo = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        }

        Teacher = loginInfo.getBoolean("teacher",true);
        return Teacher;
    }

    public void setUserNum(String usernum, Context context){
        if(loginInfo == null){
            loginInfo = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        }

        this.UserNum = usernum;
        loginInfo.edit().putString("usernum",usernum).commit();
    }

    public void setPassword(String password , Context context){
        if(loginInfo == null){
            loginInfo = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        }

        this.password = password;
        loginInfo.edit().putString("password",password).commit();
    }

    public void setTeacher(boolean isteacher,Context context){
        if(loginInfo == null){
            loginInfo = context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        }

        Teacher = isteacher;
        loginInfo.edit().putBoolean("teacher",isteacher).commit();
    }
}
