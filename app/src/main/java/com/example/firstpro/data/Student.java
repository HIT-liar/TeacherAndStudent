package com.example.firstpro.data;


public class Student implements java.io.Serializable{

    private static final long serialVersionUID = 2L;

    private String stu_num;
    private String stu_name;
    private boolean stu_gender;

    public void setStu_num(String stu_num){
        this.stu_num =stu_num;
    }

    public String getStu_num(){
        return  stu_num;
    }

    public void setStu_name(String stu_name){
        this.stu_name = stu_name;
    }

    public String getStu_name(){
        return stu_name;
    }

    public void setStu_gender(boolean gender){
        stu_gender = gender;
    }

    public boolean getStu_gender(){
        return stu_gender;
    }
}
