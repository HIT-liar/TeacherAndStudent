package com.example.demo2.database;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Question {

    private Integer questionId;
    private String classId;
    private Date time;
    private String questionText;

    public Question() {
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    @Id
    public Integer getQuestionId() {
        return questionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", classId='" + classId + '\'' +
                ", time=" + time +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}
