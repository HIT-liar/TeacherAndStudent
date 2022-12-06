package com.example.demo2.server;

import com.example.demo2.database.Account;
import com.example.demo2.database.AccountBaseDao;
import com.example.demo2.database.CourseBaseDao;
import com.example.demo2.database.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LogSign", value = "/LogSign")
public class LogSign extends MyServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogSign() {
        super();
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("不支持GET方法;");
        doPost(request,response);
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备数据库
        AccountBaseDao accountBaseDao = new AccountBaseDao();
        CourseBaseDao courseBaseDao = new CourseBaseDao();
        String sql = "select * from "+DBUtil.getAccountTableName()+" where id=?";
        Class<?> aClass = null;
        List<?> qResult = null;
        try {
            aClass = Class.forName("com.example.demo2.database.Account");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //开始处理请求
        JSONObject requestJson = getRequestJson(request);
        // 将Json转化为别的数据结构方便使用或者直接使用，进行业务处理，生成结果
        Object[] o = {requestJson.get("account")};
        try {
            qResult = accountBaseDao.query(sql,o,aClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("收到请求："+requestJson.toString());
        int Method = (int)requestJson.get("code");
        JSONObject res = new JSONObject();
        if(Method==1){
            //处理登录请求
            res.put("code",1);
            System.out.println("处理登录请求");
            //如果账号存在
            if(qResult!=null){
                Account account = (Account) qResult.get(0);
                //看密码是否正确
                if(account.getPassword().equals(requestJson.get("password"))){
                    res.put("admit",true);
                    //看是否是老师
                    res.put("isTea",account.getisteacher());
                }else{
                    res.put("admit",false);
                    res.put("info","密码不正确");
                }
            }else{
                res.put("admit",false);
                res.put("info","账号不存在");
            }
        }
        else if(Method==2){
            //处理查询请求
            res.put("code",2);
            System.out.println("处理查询请求");
            //如果账号存在
            if(qResult!=null){
                Account account = (Account) qResult.get(0);
                account.toJson(res);
            }else{
                res.put("info","账号不存在");
            }
        }
        else if(Method==3){
            //处理注册请求
            res.put("code",3);
            System.out.println("处理注册请求");
            if(qResult!=null&&qResult.size()>0){
                res.put("admin",false);
                res.put("info","账号已经存在");
            }else{
                //添加账号到数据库
                Object[] newAccount = {requestJson.get("account"),
                        requestJson.get("password"),
                        requestJson.get("job"),
                        requestJson.get("name"),
                        requestJson.get("gender")};
                accountBaseDao.add(DBUtil.getAccountTableName(),newAccount);
                //返回注册成功
                res.put("admin",true);
            }
        }else if(Method==12){
            //修改密码
            res.put("code",12);
            System.out.println("修改密码");
            if(qResult!=null){
                Account account = (Account) qResult.get(0);
                account.toJson(res);
                Object[] c = {requestJson.get("password"),requestJson.get("account")};
                int i = accountBaseDao.updatePassword(DBUtil.getAccountTableName(),c);
                if(i==1){
                    response.setStatus(200);
                }
            }else{
                res.put("info","账号不存在");
            }
        }else if(Method==13){
            //修改个人信息
            res.put("code",13);
            System.out.println("修改个人信息");
            if(qResult!=null){
                Account account = (Account) qResult.get(0);

                account.toJson(res);
                Object[] a = {requestJson.get("name"),requestJson.get("account")};
                int i = accountBaseDao.updateName(DBUtil.getAccountTableName(),a);
                if(account.getisteacher()){
                    courseBaseDao.updateTeaName(DBUtil.getCourseTableName(),a);
                }
                int j = 0;
                if((boolean)requestJson.get("gender_change")){
                    Object[] c = {requestJson.get("gender"),requestJson.get("account")};
                    j = accountBaseDao.updateGender(DBUtil.getAccountTableName(),c);
                }
                if(i==1&&j==1){
                    response.setStatus(200);
                }
            }else{
                res.put("info","账号不存在");
            }
        }

        //生成响应
        String resStr = res.toString();

        System.out.println("返回响应："+resStr);
        response.getWriter().append(resStr).flush();

    }
}
