package com.example.firstpro.data;

import android.view.View;

public class Answer {
    private String stuId;
    private int questionId;
    private int answerId;
    private String time="";
    private String ansText;
    private String stuName="";

    public Answer(String stuId,int questionId,String ansText,String time){
        this.stuId =stuId;
        this.questionId = questionId;
        this.ansText =ansText;
        this.time = time;
    }
    public String getStuId(){
        return stuId;
    }
    public int getQuestionId(){
        return questionId;
    }

    public void setAnswerId(int answerId){
        this.answerId =answerId;
    }
    public int getAnswerId(){
        return answerId;
    }

    public String getAnsText(){
        return ansText;
    }

    public void setStuName(String stuName){
        this.stuName = stuName;
    }

    public String getStuName(){
        return stuName;
    }

    public void setTime(String time){
        this.time =time;
    }
    public String getTime(){
        return time;
    }

}
