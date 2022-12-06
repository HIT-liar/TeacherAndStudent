package com.example.demo2.database;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Answer {

    private Integer answerId;
    private Integer questionId;
    private String stuId;
    private String stuName;
    private Date time;
    private String answerText;

    public Answer() {
    }

    @Id
    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }


    public Integer getQuestionId() {
        return questionId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String questionText) {
        this.answerText = questionText;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", questionId=" + questionId +
                ", stuId='" + stuId + '\'' +
                ", stuName='" + stuName + '\'' +
                ", time=" + time +
                ", answerText='" + answerText + '\'' +
                '}';
    }
}

