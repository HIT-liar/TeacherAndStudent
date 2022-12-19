package com.example.firstpro.data;

public class Question {
    private String classId;
    private int questionId;
    private String date;
    private String text;

    public Question(String classid,String text,String date){
        this.classId = classid;
        this.text = text;
        this.date =date;
    }

    public void setQuestionId(int questionid){
        this.questionId = questionid;
    }
    public int GetQuestionId(){
        return questionId;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getText(){
        return  text;
    }

    public String getClassid(){
        return classId;
    }

    public String getDate(){
        return date;
    }

}
