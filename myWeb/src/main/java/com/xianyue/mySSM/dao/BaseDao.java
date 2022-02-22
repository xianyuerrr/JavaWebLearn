package com.xianyue.mySSM.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther xianyue
 * @date 2022/2/13 - 星期日 - 20:04
 **/
public abstract class BaseDao<T> {
    // 其存在的意义是能够在不知其类型的情况下利用反射创建对象，泛型 T 只能用来声明，不能创建对象
    private Class entityClass;

    public BaseDao() {
        Type genericType = this.getClass().getGenericSuperclass();
        Type [] actualTypes = ((ParameterizedType) genericType).getActualTypeArguments();
        try {
            entityClass = Class.forName(actualTypes[0].getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DaoException("加载 Mysql 驱动出错");
        }
    }

    /**
     * @Description 填充 PreparedStatement 缺省的参数 ?
     * @param preparedStatement PreparedStatement 预编译 sql 对象
     * @param args 缺省的参数列表
     * @throws SQLException
     */
    private void setParams(PreparedStatement preparedStatement, Object... args) {
        if (args == null || args.length == 0) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            try {
                preparedStatement.setObject(i+1, args[i]);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DaoException("设置 preparedStatement 缺省参数出错");
            }
        }
    }

    /**
     * @Description 根据 ResultSet 结果集对象，装入新建的 List<T> 中并将 List<T>返回
     * @param resultSet 所得的结果集
     * @return 查询结果的 List<T> 容器
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private List<T> setValues(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            // 根据结果集获取元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 根据元数据获取列数
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                T t = (T) entityClass.getDeclaredConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i+1);
                    Object columnVal = resultSet.getObject(i+1);
                    Field field = t.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException("setValues 失败");
        }
        return list;
    }

    /**
     * @Description 根据 sql 语句进行查询
     * @param sql sql语句
     * @param args 缺省参数
     * @return 查询结果的 List<T> 容器
     */
    protected List<T> executeQuery(String sql, Object... args) {
        List<T> list = null;
        Connection connection = ConnectionUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = setValues(resultSet);
        } catch (Exception e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
            throw new DaoException("执行查询失败");
        }
        return list;
    }

    /**
     * @Description 执行非查询语句
     * @param sql sql语句
     * @param params 缺省参数
     * @return sql语句影响的行记录数目
     */
    protected int executeUpdate(String sql , Object... params){
        int res = -1;
        Connection connection = ConnectionUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams(preparedStatement, params);
            res = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException("执行更新失败");
        }
        return res;
    }
}
