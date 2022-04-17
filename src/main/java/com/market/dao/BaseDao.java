package com.market.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author lambda
 * 其为操作数据库的公共类
 * 其中需要有连接数据的相关内容
 */
public class BaseDao {
    private static String driver;
    private static String username;
    private static String url;
    private static String password;

    /**使用静态代码块，类加载的时候就初始化*/
    static {

        try {
            Properties properties = new Properties();
            //通过类加载器读取资源
            InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(is);
            driver=properties.getProperty("driver");
            username=properties.getProperty("username");
            url=properties.getProperty("url");
            password=properties.getProperty("password");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**需要获取数据库的连接*/
    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(driver);
             connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;

    }

    /**编写查询公共类*/
    public static ResultSet execute(Connection connection,String sql,PreparedStatement preparedStatement,Object...args) throws SQLException {
       preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            //为预编译语句赋值
            preparedStatement.setObject(i+1,args[i]);
        }
     return preparedStatement.executeQuery();
    }

    /**编写增删改公共类*/
    public static int Update(Connection connection,String sql,Object...args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            //为预编译语句赋值
            preparedStatement.setObject(i+1,args[0]);
        }
        int updateRows=preparedStatement.executeUpdate();
        return updateRows;
    }
    /**释放资源*/
    public  static boolean closeResources(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        boolean flag=true;
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                flag=false;
                throwables.printStackTrace();

            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                flag=false;
                throwables.printStackTrace();
            }
        }
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                flag=false;
                throwables.printStackTrace();
            }
        }
        return flag;
    }

}
