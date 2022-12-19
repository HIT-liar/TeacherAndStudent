package com.example.firstpro.WebService;

import android.util.Log;

import com.example.firstpro.data.Answer;
import com.example.firstpro.data.Class;
import com.example.firstpro.data.Me;
import com.example.firstpro.data.Question;
import com.example.firstpro.data.Student;
import com.example.firstpro.helper.StreamHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerService {

    //获取登录信息，list(0)为账号密码是否正确，list(1)为是否是教师，是教师即为true，list(2)为连接是否成功
    public List<Boolean> LoginQuery(String user_account,String user_password,String url_str){

        String result = "";
        List<Boolean> loginMessage = new ArrayList<Boolean>();
        loginMessage.add(false);
        loginMessage.add(false);
        loginMessage.add(false);

        try{
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送输入的密码和账号
            JSONObject json = new JSONObject();
            json.put("code",1);
            json.put("account",user_account);
            json.put("password",user_password);


            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();

            //等待服务器端回应
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = httpURLConnection.getInputStream();
                result = StreamHelper.InputToString(is);
                httpURLConnection.disconnect();
                is.close();
            }else{
                httpURLConnection.disconnect();
                loginMessage.set(0,false);
                loginMessage.set(1,false);
                loginMessage.set(2,false);
                return loginMessage;
            }

        }catch(Exception e){
            Log.e("error",e.getMessage());
            loginMessage.set(0,false);
            loginMessage.set(1,false);
            loginMessage.set(2,false);
            return loginMessage;
        }

        if(result!=""){
            try {
                JSONObject js = new JSONObject(result);
                //获取传来的json
                loginMessage.set(0,js.getBoolean("admit"));
                loginMessage.set(1,js.getBoolean("isTea"));
                //连接成功
                loginMessage.set(2,true);
            } catch (JSONException e){
                Log.e("error",e.getMessage());
                loginMessage.set(0,false);
                loginMessage.set(1,false);
                loginMessage.set(2,false);
                return loginMessage;
            }
//            String[] res = result.split("/");
//            //账号密码是否正确
//            if(res[0] == "1"){
//                loginMessage.set(0,true);
//            }else{
//                loginMessage.set(0,false);
//            }
//            //教师还是学生
//            if(res[0]=="1"){
//                loginMessage.set(1,true);
//            }else{
//                loginMessage.set(1,false);
//            }
//            //连接成功
//            loginMessage.set(2,true);
        }else{
            loginMessage.set(0,false);
            loginMessage.set(1,false);
            loginMessage.set(2,false);
        }

    return loginMessage;
    }

    //个人信息的查询
    public Me MessageQuery(String account, String url_str){
        Me me = null;
        String result="";

        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送输入的账号
            JSONObject json = new JSONObject();
            json.put("code",2);
            json.put("account",account);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = httpURLConnection.getInputStream();
                result = StreamHelper.InputToString(is);
                httpURLConnection.disconnect();
                is.close();
            }

        }catch (Exception e){
            Log.e("error",e.getMessage());
        }

        if(result!=""){
            try{
                JSONObject js = new JSONObject(result);
                me = new Me(js.getString("account"),js.getString("name"),js.getBoolean("gender"), js.getBoolean("job"));
            }catch (JSONException e){
                Log.e("error",e.getMessage());
            }
//          String[] res =  result.split("/");
//
//          for(int i =0 ; i<res.length ;i++){
//              //按照account、name、gender、job这样的顺序
//              String[] p_msg = res[i].split("/");
//              boolean gender;
//              boolean job;
//              if(p_msg[2].equals("true")){
//                  gender = true;
//              }else gender =false;
//              if(p_msg[3].equals("true")){
//                  job = true;
//              }else job=false;
//
//              me = new Me(p_msg[0],p_msg[1],gender,job);
//          }
        }
        return me;
    }

    public String regToRemote(Me me,String url_str){

        String account = me.getAccount();
        String password = me.getPassword();
        String name = me.getName();
        Boolean gender = me.getGender();
        Boolean isTea = me.Isteacher();

        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送输入的账号
            JSONObject json = new JSONObject();
            json.put("code",3);
            json.put("account",account);
            json.put("password",password);
            json.put("name",name);
            json.put("gender",gender);
            json.put("job",isTea);



            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();
            //这里还有更多判定，比如会收到用户名不能重复的返回码
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                httpURLConnection.disconnect();
                return "true";
            }else if(httpURLConnection.getResponseCode() == 400){
                httpURLConnection.disconnect();
                return "repeat";
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return "false";
        }
        return "false";
    }

    public String AddClassToRemote(Class the_class,String url_str){
        String class_id = the_class.getClass_id();
        String class_name = the_class.getClass_name();
        int class_num = the_class.getClass_num();
        int max_num = the_class.getMax_stu_num();
        double credit = the_class.getCourse_credit();
        String teacher_account = the_class.getTeacheraccount();
        String location = the_class.getLocation_Of_Class();
        String schedule = the_class.getTime();

        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送
            JSONObject json = new JSONObject();
                json.put("code", 4);
                json.put("classid", class_id);
                json.put("teacherid", teacher_account);
                json.put("classname", class_name);
                json.put("classnum", class_num);
                json.put("maxnum", max_num);
                json.put("credit", credit);
                json.put("location", location);
                json.put("schedule", schedule);


            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();
            //这里还有更多判定，比如会收到课程号不能重复的返回码
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                httpURLConnection.disconnect();
                return "true";
            }if(httpURLConnection.getResponseCode()==400){
                httpURLConnection.disconnect();
                return "repeat";
            }
            httpURLConnection.disconnect();
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return "false";
        }
        return "false";
    }

    //class_id 课程号，
    //url_str url
    //isTea为true时，删除classes表中内容，也要在学生选课表单中删除该课程
    //isTea为false时，只删除学生选课表单中的内容,stu_id只在这里使用
    public boolean DeleteMyClass(String class_id, String url_str,boolean isTea,String stu_id,boolean selected){


        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            //向服务器端发送
            JSONObject json = new JSONObject();
            if (isTea) {
                json.put("code", 5);
                json.put("classid", class_id);
            }else if (!selected){
                json.put("code", 6);
                json.put("classid", class_id);
                json.put("stuid",stu_id);
            }else if(selected){
                System.out.println("我选课了");
                json.put("code", 16);
                json.put("classid", class_id);
                json.put("stuid",stu_id);
            }
            System.out.println(json.toString());
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();
            //这里还有更多判定，比如会收到课程号不能重复的返回码
            System.out.println(httpURLConnection.getResponseCode());
            InputStream in = httpURLConnection.getInputStream();
            in.close();
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                httpURLConnection.disconnect();
                return true;
            }if(httpURLConnection.getResponseCode()==400){
                httpURLConnection.disconnect();
                return false;
            }
            httpURLConnection.disconnect();
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return false;
        }
        return false;

    }

    //拉取课程
    //当isTea为false且stu_chosen为false时代表学生选课，此时要拉取所有其他未被选过的课程下来
    //当isTea为false且stu_chosen为true时代表学生已选的课程，此时要拉取学生选过的课程下来
    //当isTea为true时，代表老师自己，此时需要结合teacherid进行课程拉取，此时不去管stu_chosen

    public List<Class> getClasses(String url_str,boolean isTea,String teacher_id,boolean stu_chosen, String stuid){

        List<Class> classList = new ArrayList<>();
        String result = "";

        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            JSONObject json = new JSONObject();
            if(!isTea&&!stu_chosen) {
                json.put("code", 7);
                json.put("stuid",stuid);
            }else if(!isTea&&stu_chosen){
                json.put("code", 8);
                json.put("stuid",stuid);
            } else if(isTea){
                json.put("code",9);
                json.put("teacherid",teacher_id);
            }

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();

            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is = httpURLConnection.getInputStream();
                result = StreamHelper.InputToString(is);
                is.close();
                httpURLConnection.disconnect();

            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            result = "";
        }

        if(result!=""){
            try {
                JSONObject js = new JSONObject(result);
             JSONArray jsonArray = js.getJSONArray("class");
             int size = jsonArray.length();
             for(int i=0 ;i<size ;i++){
                 JSONObject _js = jsonArray.getJSONObject(i);
                 String classid = _js.getString("classId");
                 String teacherid = _js.getString("teacherId");
                 int classnum = _js.getInt("classNum");
                 int maxnum = _js.getInt("maxStu");
                 int truenum = _js.getInt("trueStu");
                 double credit = _js.getDouble("credit");
                 String classname = _js.getString("className");
                 String location = _js.getString("location");
                 String schedule = _js.getString("schedule");
                 String teacher_name = _js.getString("teacherName");

                 Class _class = new Class(classid,classname,classnum,maxnum,credit,location,schedule,teacherid,truenum);
                 _class.setTeacher_name(teacher_name);

                 classList.add(_class);
             }
            }catch (JSONException e){
                Log.e("error",e.getMessage());
            }
        }
        return  classList;
    }

    public boolean AddChoice(String classid,String stuid,String url_str){
        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送学生及其所选课程
            JSONObject json = new JSONObject();
            json.put("code",10);
            json.put("classid",Integer.valueOf(classid));
            json.put("stuid",stuid);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();

            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                httpURLConnection.disconnect();
                return true;
            }else {
                httpURLConnection.disconnect();
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return false;
        }
        return false;
    }

    public List<Student> StuChosenClass(String classid,String url_str){
        List<Student> students = new ArrayList<>();
        String result ="";
        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            JSONObject json = new JSONObject();
            json.put("code",11);
            json.put("classid",classid);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();

            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is = httpURLConnection.getInputStream();
                result = StreamHelper.InputToString(is);
                httpURLConnection.disconnect();
                is.close();
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            result = "";
        }

        if(result!=""){
            try {
                JSONObject js = new JSONObject(result);
                JSONArray jsonArray = js.getJSONArray("student");
                int size = jsonArray.length();
                for(int i=0 ;i<size ;i++){
                    JSONObject _js = jsonArray.getJSONObject(i);
                    String stu_account = _js.getString("stuid");
                    String stu_name = _js.getString("name");
                    boolean gender = _js.getBoolean("gender");

                    Student s= new Student(stu_account,stu_name,gender);
                    students.add(s);
                }
            }catch (JSONException e){
                Log.e("error",e.getMessage());
            }
        }
        return  students;
    }

    public boolean ChangeMess(String user_id,String url_str,boolean ispassword,String new_pass,String new_name,Boolean genderChange ,Boolean new_Gender){
        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送输入的账号
            JSONObject json = new JSONObject();
            if(ispassword) {
                json.put("code", 12);
                json.put("account",user_id);
                json.put("password",new_pass);
            }else{
                json.put("code",13);
                json.put("name",new_name);
                json.put("account",user_id);
                json.put("gender_change",genderChange.booleanValue());
                json.put("gender",new_Gender.booleanValue());
            }
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();
            //这里还有更多判定，比如会收到用户名不能重复的返回码
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                httpURLConnection.disconnect();
                return true;
            }else if(httpURLConnection.getResponseCode() == 400){
                httpURLConnection.disconnect();
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return false;
        }
        return false;
    }

    public boolean SendQues(Question ques,String url_str){
        try {
            System.out.println("已执行");
            System.out.println("发送到"+url_str);
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送输入的账号
            JSONObject json = new JSONObject();
            json.put("code",14);
            System.out.println("classid:"+ques.getClassid());
            json.put("classId",ques.getClassid());
            json.put("time",ques.getDate());
            json.put("questionText",ques.getText());

            System.out.println(json.toString());

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();
            //这里还有更多判定，比如会收到用户名不能重复的返回码
            System.out.println(httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                httpURLConnection.disconnect();
                return true;
            }else if(httpURLConnection.getResponseCode() == 400){
                httpURLConnection.disconnect();
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return false;
        }
        return false;
    }

    public List<Question> getQuestion(String classId,String url_str){
        List<Question> questions = new ArrayList<>();
        String result ="";
        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();


            JSONObject json = new JSONObject();

                json.put("code", 15);
                json.put("classId", classId);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();

            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is = httpURLConnection.getInputStream();
                result = StreamHelper.InputToString(is);
                httpURLConnection.disconnect();
                is.close();
            }else{
                httpURLConnection.disconnect();
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            result = "";
        }
 System.out.println(result);
        if(result!=""){
            try {
                JSONObject js = new JSONObject(result);
                JSONArray jsonArray = js.getJSONArray("question");
                int size = jsonArray.length();
                for(int i=0 ;i<size ;i++){
                    JSONObject _js = jsonArray.getJSONObject(i);
                    String time = _js.getString("time");
                    String text = _js.getString("questionText");
                    int q_id = _js.getInt("questionId");

                    Question s = new Question(classId,text,time);
                    s.setQuestionId(q_id);
                   questions.add(s);
                }
            }catch (JSONException e){
                Log.e("error",e.getMessage());
            }
        }
        return  questions;
    }

    public List<Answer> getAnswer(int quesId,String url_str){
        List<Answer> answers = new ArrayList<>();
        String result ="";
        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();


            JSONObject json = new JSONObject();

            json.put("code", 17);
            json.put("questionId", quesId);


            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();

            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is = httpURLConnection.getInputStream();
                result = StreamHelper.InputToString(is);
                httpURLConnection.disconnect();
                is.close();
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            result = "";
        }
System.out.println(result);
        if(result!=""){
            try {
                JSONObject js = new JSONObject(result);
                JSONArray jsonArray = js.getJSONArray("question");
                int size = jsonArray.length();
                for(int i=0 ;i<size ;i++){
                    JSONObject _js = jsonArray.getJSONObject(i);
                    String time = _js.getString("time");
                    String text = _js.getString("answerText");
                    int a_id = _js.getInt("answerId");
                    String stu_name = _js.getString("stuName");
                    String stu_id = _js.getString("stuId");

                    Answer ans = new Answer(stu_id,quesId,text,time);
                    ans.setStuName(stu_name);
                    ans.setAnswerId(a_id);

                     answers.add(ans);
                }
            }catch (JSONException e){
                Log.e("error",e.getMessage());
            }
        }
        return  answers;
    }

    public boolean SendAnswer(Answer answer , String url_str){
        try {
            URL url = new URL(url_str);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.connect();

            //向服务器端发送输入的账号
            JSONObject json = new JSONObject();
            json.put("code",18);
            json.put("stuId",answer.getStuId());
            json.put("questionId",answer.getQuestionId());
            json.put("time",answer.getTime());
            json.put("answerText",answer.getAnsText());



            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter opw = new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter writer = new BufferedWriter(opw);
            writer.write(json.toString());
            writer.close();
            opw.close();
            outputStream.close();
            //这里还有更多判定，比如会收到用户名不能重复的返回码
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                httpURLConnection.disconnect();
                return true;
            }else if(httpURLConnection.getResponseCode() == 400){
                httpURLConnection.disconnect();
            }
        }catch (Exception e){
            Log.e("error",e.getMessage());
            return false;
        }
        return false;
    }

}
