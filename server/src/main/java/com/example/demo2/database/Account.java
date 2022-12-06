package com.example.demo2.database;

import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    private int id;
    private String password;
    private String name;
    private boolean gender;
    private boolean isteacher;

    public Account(int id, String pw, boolean b) {
        this.id = id;
        this.password = pw;
        this.isteacher = b;
    }

    public Account() {

    }
    public Account(JSONObject json){
        this.id = (int)json.get("account");
        this.password = (String)json.get("password");
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getisteacher() {
        return isteacher;
    }

    public void setisteacher(boolean isT) {
        isteacher = isT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public void toJson(JSONObject json){
        json.put("account",getId());
        json.put("name",getName());
        json.put("gender",getGender());
        json.put("job", getisteacher());
    }
    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", isTeacher=" + isteacher +
                '}';
    }
}
