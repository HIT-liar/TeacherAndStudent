package com.example.demo2.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerBaseDao extends BaseDao{
    private final String tableName = "answer_info";

    /**
     * @param obj = {stuId,questionId,time,answerText,stuName}
     * @return 添加的行数
     */
    public int addAnswer(Object[] obj){
        String sql = "insert into "+tableName+"(stuId,questionId,time,answerText,stuName) values(?,?,?,?,?)";
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
     * @return 某个问题下的所有回答
     */
    public List<Answer> queryAnswer(Object[] obj){
        String sql = "select * from "+tableName+" where questionId=?";
        List<Answer> result = new ArrayList<>();
        try{
            result.addAll(query(sql,obj,Answer.class));
        }catch (Exception e){
            System.out.println("查询回答出现异常");
            e.printStackTrace();
        }
        return result;
    }
}
