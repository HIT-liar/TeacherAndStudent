package com.example.demo2.server;

import com.example.demo2.database.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentCourse", value = "/StudentCourse")
public class StudentCourse extends MyServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentCourse(){
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //开始处理请求
        JSONObject requestJson = getRequestJson(request);
        System.out.println("收到请求："+requestJson.toString());
        //准备数据库
        CourseBaseDao courseBaseDao = new CourseBaseDao();
        AccountBaseDao accountBaseDao = new AccountBaseDao();
        AssociationBaseDao associationBaseDao = new AssociationBaseDao();
        //处理请求

        int Method = (int)requestJson.get("code");
        JSONObject res = new JSONObject();
        //6 退选
        if(Method==6){
            res.put("code",6);
            System.out.println("学生退选课程");
            Object[] o = {requestJson.get("stuid"),requestJson.get("classid")};
            int i = associationBaseDao.stuUnselect(DBUtil.getAssociationTableName(),o);
            Object[] classId = {requestJson.get("classid")};
            courseBaseDao.decreaseTureStu(DBUtil.getCourseTableName(),classId);
            if(i==1){
                response.setStatus(200);
            }else{
                response.setStatus(400);
            }
        }
        //7 查询未选
        else if (Method==7) {
            res.put("code",7);
            System.out.println("学生查询能选择的课程");
            Object[] o = {requestJson.get("stuid")};
            List<Course> unSelectClasses;
            try{
                unSelectClasses = new ArrayList<>(associationBaseDao.stuQueryUnselected(o));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            res.put("class",new JSONArray(unSelectClasses));
        }
        //8 查询已选
        else if (Method==8) {
            res.put("code",8);
            System.out.println("学生查询已经选择的课程");
            Object[] o = {requestJson.get("stuid")};
            List<Course> SelectClasses;
            try{
                SelectClasses = new ArrayList<>(associationBaseDao.stuQuerySelected(o));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            res.put("class",new JSONArray(SelectClasses));
        }
        //16 选课
        else if (Method==16) {
            res.put("code",16);
            System.out.println("学生选择课程");
            Object[] o = {requestJson.get("stuid"),requestJson.get("classid")};
            int i = associationBaseDao.stuSelect(DBUtil.getAssociationTableName(),o);
            Object[] classId = {requestJson.get("classid")};
            courseBaseDao.increaseTureStu(DBUtil.getCourseTableName(),classId);
            if(i==1){
                response.setStatus(200);
            }else{
                response.setStatus(400);
            }
        }
        //生成响应
        String resStr = res.toString();

        System.out.println("返回响应："+resStr);
        response.getWriter().append(resStr).flush();
    }


}
