package com.example.demo2.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionBaseDao extends BaseDao{
    private final String tableName = "question_info";

    /**
     *
     * @param obj = {classId,time,questionText}
     * @return 添加的行数
     */
    public int addQuestion(Object[] obj){
        String sql = "insert into "+tableName+"(classId,time,questionText) values(?,?,?)";
        int i;
        try {
            i = updateData(sql, obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    /**
     *
     * @param obj = {questionId}
     * @return 某个课程下的所有问题
     */
    public List<Question> queryQuestion(Object[] obj){
        String sql = "select * from "+tableName+" where classId=?";
        List<Question> result = new ArrayList<>();
        try{
            result.addAll(query(sql,obj,Question.class));
        }catch (Exception e){
            System.out.println("查询问题出现异常");
            e.printStackTrace();
        }
        return result;
    }
}
