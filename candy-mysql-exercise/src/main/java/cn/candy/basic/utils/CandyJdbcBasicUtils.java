package cn.candy.basic.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 使用jdbcUtils工具类进行链接数据库
 * 本质上还是直接链接数据库 但是封装了 获取链接及释放链接资源
 *
 * @author : buxi
 * @date : 2020-01-16 10:12
 **/
public class CandyJdbcBasicUtils {
    /**
     * mysql远程服务器
     */
    private static String url;
    /**
     * mysql远程用户名
     */
    private static String user;
    /**
     * mysql远程用户密码
     */
    private static String password;
    /**
     * mysql驱动
     */
    private static String driver;

    //使用静态代码块初始化类
    static {
        //sql配置文件名称
        String mysqlPropertiesName = "mysql.properties";
        //1.创建Properties对象 使用properties读取配置
        Properties properties = new Properties();
        //2.尝试使用当前类加载器读取properties文件
        InputStream inputStream = CandyJdbcBasicUtils.class.getClassLoader().getResourceAsStream(mysqlPropertiesName);
        try {
            //确认输入流存在
            assert inputStream != null;
            //3.从文件流中加载properties配置文件
            properties.load(inputStream);
            //4.读取properties配置
            url = String.format(properties.getProperty("mysql.url"), System.getenv(properties.getProperty("mysql.host")));
            user = System.getenv(properties.getProperty("mysql.user"));
            password = System.getenv(properties.getProperty("mysql.password"));
            driver = properties.getProperty("mysql.driver");
            try {
                //5.加载驱动
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new Exception("使用properties配置,读取driver加载失败.尝试读取文件为:" + mysqlPropertiesName);
            }
        } catch (Exception e) {
            e.getMessage();
            try {
                throw new Exception("无法加载" + mysqlPropertiesName + "配置文件,无法从properties中加载配置");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获得Connection链接
     *
     * @return Connection sql链接
     */
    public static Connection getConnection() throws SQLException {
        //获得connection链接对象
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭资源/链接 Statement Connection
     *
     * @param connection sql链接
     * @param statement  执行语句
     */
    public static void close(Connection connection, Statement statement) throws SQLException {
        //关闭statement
        if (statement != null) {
            statement.close();
        }
        //关闭connection
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * 关闭资源/链接 ResultSet Statement Connection - 重载
     *
     * @param connection sql链接
     * @param statement  可执行语句 兼容prepareStatement
     * @param resultSet  查询结果集
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        //关闭resultSet
        if (resultSet != null) {
            resultSet.close();
        }
        //调用已有方法 关闭资源
        close(connection, statement);
    }
}
