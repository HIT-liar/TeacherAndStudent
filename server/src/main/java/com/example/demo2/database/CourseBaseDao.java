package com.example.demo2.database;

import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

//import static jdk.internal.org.jline.utils.Colors.s;

public class CourseBaseDao extends BaseDao{
    /**
     * @param obj {classId,teacherId,className,teacherName,classNum,maxStu,location,schedule,credit}
     */
    public int add(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "insert into " + tableName + " values(?,?,?,?,?,?,0,?,?,?)";
        //调用方法，返回值为修改的行数
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;

    }
//
//    public static void main(String[] args) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
////        Object[] o = {"cs61b",12345,"python",48,50,"正心601","周三1，2",3};
//        CourseBaseDao c = new CourseBaseDao();
////        c.add(DBUtil.getCourseTableName(),o);
//        String sql = "select * from "+DBUtil.getCourseTableName();
//        Class<?> aClass = null;
//        try {
//            aClass = Class.forName("com.example.demo2.database.Course");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        c.query(sql,null,aClass);
//    }
    /**
     * @param obj {classId}
     */
    public int deleteById(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "delete from " + tableName + " where classId=?";
        //调用方法，返回值为修改的行数
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    /**
     * @param obj {classId}
     */
    public void increaseTureStu(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "update " + tableName + " set trueStu = trueStu+1 where classId=?";
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(i);
    }
    /**
     * @param obj {classId}
     */
    public void decreaseTureStu(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "update " + tableName + " set trueStu = trueStu-1 where classId=?";
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(i);
    }
    /**
     * @param obj {teacherName,teacherId}
     */
    public void updateTeaName(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "update " + tableName + " set teacherName=? where teacherId=?";
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(i);
    }
    /**老师查看自己已经添加的课程
     * @param obj {account}
     * @return 直接返回课程而不是关联表
     */
    public List<Course> teaQueryAdded(Object[] obj) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String sql = "select * from "+DBUtil.getCourseTableName()+" where teacherId = ?";
        List<Course> courseList = query(sql,obj, Course.class);
        return courseList;
    }

}
