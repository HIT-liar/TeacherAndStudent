package com.example.demo2.database;

import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AssociationTable {
    private Long id;
    private int accountId;
    private String classId;

    public AssociationTable() {
    }

    public AssociationTable(int accountId, String classId) {
        this.accountId = accountId;
        this.classId = classId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountid) {
        this.accountId = accountid;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classid) {
        this.classId = classid;
    }

    @Override
    public String toString() {
        return "AssociationTable{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", classId='" + classId + '\'' +
                '}';
    }
}
