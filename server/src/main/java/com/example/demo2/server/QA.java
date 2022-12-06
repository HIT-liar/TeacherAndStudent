package com.example.demo2.server;

import com.example.demo2.database.*;
import com.mysql.cj.xdevapi.JsonArray;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "QA", value = "/QA")
public class QA extends MyServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QA(){
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备数据库
        AccountBaseDao accountBaseDao = new AccountBaseDao();
        AnswerBaseDao answerBaseDao = new AnswerBaseDao();
        QuestionBaseDao questionBaseDao = new QuestionBaseDao();
        CourseBaseDao courseBaseDao = new CourseBaseDao();

        //开始处理请求

        JSONObject requestJson = getRequestJson(request);
        System.out.println("收到请求："+requestJson.toString());
        int Method = (int)requestJson.get("code");
        JSONObject res = new JSONObject();
        //教师添加某一门课的问题
        if(Method==14){
            res.put("code",14);
            System.out.println("教师添加某一门课的问题");
            Object[] questionInfo = {requestJson.get("classId"),requestJson.get("time"),requestJson.get("questionText")};
            int i = questionBaseDao.addQuestion(questionInfo);
            if(i==1){
                response.setStatus(200);
            }else{
                response.setStatus(400);
            }
        }
        if (Method==15) {
            //查询某一门课的全部问题
            res.put("code",15);
            System.out.println("查询某一门课的全部问题");
            List<Question> allQ;
            Object[] classId = {requestJson.get("classId")};
            allQ = new ArrayList<>(questionBaseDao.queryQuestion(classId));
            res.put("question",new JSONArray(allQ));
        } else if (Method==17) {
            //查询一个问题的全部回答
            res.put("code",17);
            System.out.println("查询一个问题的全部回答");
            List<Answer> allA;
            Object[] questionId = {requestJson.get("questionId")};
            allA = new ArrayList<>(answerBaseDao.queryAnswer(questionId));
            res.put("question",new JSONArray(allA));
        } else if (Method==18) {
            //学生提供一个问题的答案
            res.put("code",18);
            System.out.println("学生提供一个问题的答案");
            Object[] answerInfo = {requestJson.get("stuId"),requestJson.get("questionId"),requestJson.get("time"),requestJson.get("answerText"),null};
            Object[] stuId = {requestJson.get("stuId")};
            String stuName = null;
            try{
                stuName = accountBaseDao.queryNameById(DBUtil.getAccountTableName(),stuId);
            }catch (Exception e){
                System.out.println("查询学生姓名失败");
                e.printStackTrace();
            }
            answerInfo[4] = stuName;
            int i = answerBaseDao.addAnswer(answerInfo);
            if(i==1){
                response.setStatus(200);
            }else{
                response.setStatus(400);
            }
        }


        String resStr = res.toString();
        System.out.println("返回响应："+resStr);
        response.getWriter().append(resStr).flush();
    }

}
