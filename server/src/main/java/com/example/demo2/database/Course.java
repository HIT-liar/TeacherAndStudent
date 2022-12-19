package com.example.demo2.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course {
    private String classId;
    private Long teacherId;
    private String className;
    private String teacherName;
    private int classNum;//课时数
    private int maxStu;
    private int trueStu;
    private String location;
    private String schedule;
    private float credit;

    public Course(){

    }

    public Course(String classId, Long teacherId, String className, int classNum, int maxStu, String location, String schedule, int credit) {
        this.classId = classId;
        this.teacherId = teacherId;
        this.className = className;
        this.classNum = classNum;
        this.maxStu = maxStu;
        this.location = location;
        this.schedule = schedule;
        this.credit = credit;
    }

    public void setClassId(String id) {
        this.classId = id;
    }

    @Id
    public String getClassId() {
        return classId;
    }
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public int getMaxStu() {
        return maxStu;
    }

    public void setMaxStu(int maxStu) {
        this.maxStu = maxStu;
    }

    public int getTrueStu() {
        return trueStu;
    }

    public void setTrueStu(int trueStu) {
        this.trueStu = trueStu;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{" +
                "classId='" + classId + '\'' +
                ", teacherId=" + teacherId +
                ", className='" + className + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", classNum=" + classNum +
                ", maxStu=" + maxStu +
                ", trueStu=" + trueStu +
                ", location='" + location + '\'' +
                ", schedule='" + schedule + '\'' +
                ", credit=" + credit +
                '}';
    }
}
