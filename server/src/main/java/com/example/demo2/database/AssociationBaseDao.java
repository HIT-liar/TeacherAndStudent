package com.example.demo2.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class AssociationBaseDao extends BaseDao{

    /**学生选课
     * @param obj {accountId,classId}
     */
    public int stuSelect(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "insert into " + tableName + "(accountId,classId) values(?,?)";
        //调用方法，返回值为修改的行数
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;

    }

    /**学生退选
     * @param obj {accountId,classId}
     */
    public int stuUnselect(String tableName, Object[] obj) {
        //写入带参数的sql语句
        String sql = "delete from " + tableName + " where accountId=? and classId=?";
        //调用方法，返回值为修改的行数
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;

    }

    /**学生查看自己已经选择的课程
     * @param obj {account}
     * @return 直接返回课程而不是关联表
     */
    public List<Course> stuQuerySelected(Object[] obj) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        //写入带参数的sql语句
        String sql = "select * from "+DBUtil.getAssociationTableName()+" where accountId=?";
        //待返回的课程列表
        List<Course> courseList = new ArrayList<>();
        //用于接收该学生已选的所有课程的id
        List<AssociationTable> associationTableList;
        associationTableList = new ArrayList<>(query(sql,obj, AssociationTable.class));
        //通过id在课程表中找到课程
        String sql1 = "select * from "+DBUtil.getCourseTableName()+" where classId=?";
        CourseBaseDao courseBaseDao = new CourseBaseDao();
        for (AssociationTable a:associationTableList) {
            Object[] o = {a.getClassId()};
            List<Course> aCourse = courseBaseDao.query(sql1,o,Course.class);
            System.out.println("查到已选课程："+aCourse.get(0));
            courseList.add(aCourse.get(0));
        }
        return courseList;
    }
    /**学生查看自己没有选择的课程
     * @param obj {account}
     * @return 直接返回课程而不是关联表
     */
    public List<Course> stuQueryUnselected(Object[] obj) throws SQLException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {

        String sql1 = "select * from "+DBUtil.getCourseTableName();
        CourseBaseDao courseBaseDao = new CourseBaseDao();
        List<Course> allCourse = new ArrayList<>(courseBaseDao.query(sql1,null, Course.class));
        List<Course> selected = new ArrayList<>(stuQuerySelected(obj));
        if(selected.size()==0){
            return allCourse;
        }
        List<String> allCourseId = new ArrayList<>();
        for(Course a:allCourse){
            allCourseId.add(a.getClassId());
        }
        List<String> SCourseId = new ArrayList<>();
        for(Course s:selected){
            SCourseId.add(s.getClassId());
        }
        Set<Course> unSelected = new HashSet<>();
        for(String a:allCourseId){
            if(!SCourseId.contains(a)){
                for(Course c:allCourse){
                    if(c.getClassId().equals(a)){
                        unSelected.add(c);
//                        System.out.println(c.getClassId());
//                        System.out.println(unSelected.size());
                    }
                }
            }
        }

        return new ArrayList<>(unSelected);
    }

}
