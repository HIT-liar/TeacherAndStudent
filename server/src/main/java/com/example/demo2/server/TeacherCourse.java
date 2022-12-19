package com.example.demo2.server;

import com.example.demo2.database.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "TeacherCourse", value = "/TeacherCourse")
public class TeacherCourse extends MyServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherCourse(){
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备数据库
        CourseBaseDao courseBaseDao = new CourseBaseDao();
        AccountBaseDao accountBaseDao = new AccountBaseDao();
        AssociationBaseDao associationBaseDao = new AssociationBaseDao();
        QuestionBaseDao questionBaseDao = new QuestionBaseDao();
        //开始处理请求
        JSONObject requestJson = getRequestJson(request);
        System.out.println("收到请求："+requestJson.toString());
        int Method = (int)requestJson.get("code");
        JSONObject res = new JSONObject();
        //code4 老师添加
        if(Method==4){
            res.put("code",4);
            //通过教师id查找教师name
            Object[] teacherId = {requestJson.get("teacherid")};
            String sql = "select * from "+ DBUtil.getAccountTableName()+" where id=?";
            String teacherName = null;
            try {
                List<Account> query = accountBaseDao.query(sql, teacherId, Account.class);
                if(query!=null){
                    teacherName = query.get(0).getName();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //把所有信息添加到课程信息里
            Object[] courseInfo = {requestJson.get("classid"),requestJson.get("teacherid"),requestJson.get("classname"),
                    teacherName,requestJson.get("classnum"),requestJson.get("maxnum"),requestJson.get("location"),requestJson.get("schedule"),requestJson.get("credit")};
            int i = courseBaseDao.add(DBUtil.getCourseTableName(),courseInfo);
            if(i==1){
                response.setStatus(200);
            }else{
                response.setStatus(400);
            }

        } else if (Method==5) {
            //code5 老师删除
            res.put("code",5);
            System.out.println("老师删除课程");
            Object[] classId = {requestJson.get("classid")};
            int i = courseBaseDao.deleteById(DBUtil.getCourseTableName(),classId);
            if(i==1){
                response.setStatus(200);
            }else{
                response.setStatus(400);
            }
        } else if (Method==9) {
            //code9 老师查看自己添加的课
            res.put("code",9);
            System.out.println("老师查看自己添加的课");
            Object[] teacherId = {requestJson.get("teacherid")};
            //String sql = "select * from "+DBUtil.getCourseTableName()+" where teacherId=?";
            List<Course> myCourses = null;
            try{
                myCourses = courseBaseDao.teaQueryAdded(teacherId);
                 //courseBaseDao.query(sql, teacherId, Course.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            res.put("class",new JSONArray(myCourses));
        } else if (Method==11) {
            //code11 老师查看选自己某一门课的学生
            res.put("code",11);
            System.out.println("老师查看选自己某一门课的学生");
            Object[] classId = {requestJson.get("classid")};
            String sql = "select * from "+DBUtil.getCourseTableName()+" where classId=?";
            List<AssociationTable> query;
            try{
                query = associationBaseDao.query(sql, classId, AssociationTable.class);
            } catch (Exception e) {
            throw new RuntimeException(e);
        }
            String sql1 = "select * from "+ DBUtil.getAccountTableName()+" where id=?";
            JSONArray students = new JSONArray();
            for (AssociationTable a : query) {
                Object[] o = {a.getAccountId()};
                List<Account> account;
                try{
                    account = accountBaseDao.query(sql1, o, Account.class);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                JSONObject aStu = new JSONObject();
                aStu.put("stuid",account.get(0).getId());
                aStu.put("name",account.get(0).getName());
                aStu.put("gender",account.get(0).getGender());
                students.put(aStu);
            }
            res.put("student",students);
        }

        //生成响应
        String resStr = res.toString();

        System.out.println("返回响应："+resStr);
        response.getWriter().append(resStr).flush();
    }
}
