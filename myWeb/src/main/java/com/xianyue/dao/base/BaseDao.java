package com.xianyue.dao.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.xianyue.ConfigRead.MySqlConfig;

/**
 * @auther xianyue
 * @date 2022/2/13 - 星期日 - 20:04
 **/
public abstract class BaseDao<T> {
    public static String driver, url, user, pwd;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        driver = MySqlConfig.get("driverClass");
        url = MySqlConfig.get("url");
        user = MySqlConfig.get("user");
        pwd = MySqlConfig.get("pwd");
    }

    private Connection connection;

    // 其存在的意义是能够在不知其类型的情况下利用反射创建对象，泛型 T 只能用来声明，不能创建对象
    private Class entityClass;

    /**
     * @Description 没有必要使用，BaseDao 是为了提供基础的与数据库交互的功能，在其它具体 Dao 类继承时就确定了要操作的对象类型，
     * 继承时直接将泛型 T 写为目标对象类型使用反射获取填入参数即可，无需传参确定。
     * @param clazz 操作数据的类型
     */
    public BaseDao(Class clazz) {
        entityClass = clazz;
        System.out.println(entityClass);

    }

    public BaseDao() {
        Type genericType = this.getClass().getGenericSuperclass();
        Type [] actualTypes = ((ParameterizedType) genericType).getActualTypeArguments();
        try {
            entityClass = Class.forName(actualTypes[0].getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(entityClass);
    }

    /**
     * @Description 获取 connection，如果 connection 不为 null，就直接返回。若为 null，
     * 则根据默认配置创建新连接并对 connection 进行赋值并返回。
     * @return connection
     */
    protected Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            // 加载驱动
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
            System.out.println("连接数据库失败");
            return null;
        }
        System.out.println("数据库连接成功 : " + connection);
        return connection;
    }

    /**
     * @Description 填充 PreparedStatement 缺省的参数 ?
     * @param preparedStatement PreparedStatement 预编译 sql 对象
     * @param args 缺省的参数列表
     * @throws SQLException
     */
    private void setParams(PreparedStatement preparedStatement, Object... args) throws SQLException {
        if (args == null || args.length == 0) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i+1, args[i]);
        }
        return;
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
    private List<T> setValues(ResultSet resultSet)
            throws SQLException, NoSuchFieldException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
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
        if (connection == null) { getConnection(); }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            list = setValues(resultSet);
        } catch (SQLException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getClass() + " : " + e.getMessage());
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
        if (connection == null) { getConnection(); }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams(preparedStatement, params);
            res = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
