package com.example.demo2.database;




import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountBaseDao extends BaseDao{
    //sql是sql语句 object数组里的是sql语句里参数的值

    /**
     * @param obj {id,password,isTeacher,name,gender}
     */
    public void add(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "insert into " + tableName + " values(?,?,?,?,?)";
        //调用方法，返回值为修改的行数
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(i);

    }

    /**
     * @param obj {id}
     */
    public void deleteById(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "delete from " + tableName + " where id = ?";
        //调用方法，返回值为修改的行数
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(i);
    }

    /**
     * @param obj {password,id}
     */
    public int updatePassword(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "update " + tableName + " set password = ? where id = ?";
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    /**
     * @param obj {name,id}
     */
    public int updateName(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "update " + tableName + " set name = ? where id = ?";
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    /**
     * @param obj {gender,id}
     */
    public int updateGender(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "update " + tableName + " set gender = ? where id = ?";
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    /**
     * @param obj {id}
     */
    public String queryNameById(String tableName, Object[] obj) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        //写入带参数的sql语句
        String sql = "select * from " + tableName + " where id = ?";
        List<Account> accounts = query(sql, obj, Account.class);
        return accounts.get(0).getName();
    }

}

