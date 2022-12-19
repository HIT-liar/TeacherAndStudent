package com.example.demo2.database;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public abstract class BaseDao {
    public int updateData(String sql, Object[] obj) throws SQLException {
        //获取connection对象
        Connection connection = DBUtil.getConnection();
        //获取预处理搬运工对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //通过元数据来获取sql语句参数个数，方便之后进行赋值
        int parameterCount = preparedStatement.getParameterMetaData().getParameterCount();
        for (int i = 1; i <= parameterCount; i++) {
            //调用预处理搬运工的setObject方法来进行赋值
            //因为数据库中从1开始，数组里从0开始，所以一定要保持一一对应
            preparedStatement.setObject(i, obj[i - 1]);
        }
        int i = preparedStatement.executeUpdate();
        //最后记着关闭
        DBUtil.close(connection, preparedStatement);
        return i;
    }
    public <T> List<T> query(String sql, Object[] objs, Class<T> tClass) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InvocationTargetException {
        //首先判断sql和反射对象tClass是否为空，如果为空则抛出异常
        if (sql == null || tClass == null) {
            throw new NullPointerException();
        }
        //获取connection对象
        Connection connection = DBUtil.getConnection();
        //获取预处理搬运工对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //通过元数据获得sql语句里的参数个数也就是？的个数
        int parameterCount = preparedStatement.getParameterMetaData().getParameterCount();
        //判断数组的容量是否与sql语句参数的个数一样
        if (objs!=null&&parameterCount == objs.length) {
            //如果一样的话则将数组中的数值注入sql语句
            for (int i = 1; i <= parameterCount; i++) {
                preparedStatement.setObject(i, objs[i - 1]);
            }
        }
        //执行sql语句并获取resultSet对象
        ResultSet resultSet = preparedStatement.executeQuery();
        //获取元数据对象
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取表中字段的个数
        int columnCount = metaData.getColumnCount();
        //创建带泛型的集合，来进行对象的存储
        ArrayList<T> ts = new ArrayList<>();
        //while循环是一行一行的遍历
        while (resultSet.next()) {
            //通过反射来获取对象
            T t = tClass.getConstructor().newInstance(null);
            //for循环是一列一列的遍历
            for (int i = 1; i <= columnCount; i++) {
                //获取列的字段名
                String columnName = metaData.getColumnName(i);
                //通过字段名获取值
                Object values = resultSet.getObject(columnName);
                //course 的id总是加不上去不知道为什么.
//                if(columnName.equals("classId")){
//                    ((Course)t).setId((String) values);
//                }
                //通过BeanUtil来对对象进行赋值，t表示的对象，columnName表示的字段名，也就是对象的属性名（之前创建对象的时候一定要把属性名和字段名设置成一样的！！！）
                //values是这一行这个字段对应的值
                BeanUtils.setProperty(t, columnName, values);
            }
            //将赋值完的对象放入集合中
            ts.add(t);
            System.out.println(t.toString());

        }
        //通过三目运算符判断集合是否为空，并返回集合
        return ts.size() != 0 ? ts : new ArrayList<>();
    }

}
