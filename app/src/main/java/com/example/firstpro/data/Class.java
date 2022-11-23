package com.example.firstpro.data;

public class Class implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private String class_name;//课程名称
    private int class_num; //课时数
    private int Max_stu_num;//最大选课人数
    private int true_stu_num;//真实的选课人数
    private double course_credit;//学分
    private String Location_Of_Class;//上课地点的描述
    private String Time;//上课的时间
    private String teacheraccount;//相应的教师

    public Class(){

    }
    public Class(String class_name,int class_num,int Max_stu_num,double course_credit,
                 String location_Of_Class,String time,String teacheraccount){
        this.class_num =class_num;
        this.class_name =class_name;
        this.Max_stu_num = Max_stu_num;
        this.course_credit = course_credit;
        this.Location_Of_Class = location_Of_Class;
        this.Time = time;
        this.teacheraccount = teacheraccount;
    }

    public void setClass_name(String class_name){
        this.class_name=class_name;
    }

    public String getClass_name(){
        return class_name;
    }

    public void setClass_num(int class_num){
        this.class_num = class_num;
    }

    public int getClass_num(){
        return class_num;
    }

    public void setMax_stu_num(int Max_stu_num){
        this.Max_stu_num=Max_stu_num;
    }

    public int getMax_stu_num(){
        return Max_stu_num;
    }

    public void setTrue_stu_num(int true_stu_num){
        this.true_stu_num=true_stu_num;
    }

    public int getTrue_stu_num(){
        return true_stu_num;
    }

    public void setCourse_credit(double course_credit){
        this.course_credit=course_credit;
    }

    public double getCourse_credit(){
        return course_credit;
    }

    public void setLocation_Of_Class(String location_Of_Class){
        this.Location_Of_Class=location_Of_Class;
    }

    public String getLocation_Of_Class(){
        return Location_Of_Class;
    }

    public void setTime(String time){
        this.Time=time;
    }

    public String getTime(){
        return Time;
    }

    public void setTeacheraccount(String teacheraccount){
        this.teacheraccount = teacheraccount;
    }
    public String getTeacheraccount(){
        return teacheraccount;
    }

}
