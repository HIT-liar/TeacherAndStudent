package com.example.firstpro.data;

public class Me {


    private String account;//账号（职工号/学号）
    private String password;//密码
    private String name;//名字
    private boolean gender;//性别
    private boolean isteacher = true;//是否为老师

    public Me(String account,String password,String name,boolean gender,boolean job){
        this.account = account;
        this.password = password;
        this.name = name;
        this.gender=gender;
        isteacher =job;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public String getAccount(){
        return account;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setGender(boolean gender){
        this.gender=gender;
    }

    public boolean getGender(){
        return gender;
    }

    public void Getjob(boolean job){
        isteacher =job;
    }

    public boolean Isteacher() {
        return isteacher;
    }
}
