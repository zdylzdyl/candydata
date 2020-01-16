package cn.candy.basic.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * c3p0数据库连接池
 * <p>
 * 使用C3p0 链接池连接MySQL
 *
 * @author : buxi
 * @date : 2020-01-16 14:19
 **/
public class CandyJdbcC3p0Utils {
    /**
     * 数据库连接池
     */
    private static DataSource dataSource = null;

    /*
     * 配置数据库连接池comboPooledDataSource
     */
    static {
        // 自动加载src目录下面的c3p0的配置文件，【c3p0-config.xml】
        ComboPooledDataSource c3p0Pool = new ComboPooledDataSource();
        //然而我没有配置xml或者properties 手动创建参数
        Properties properties = new Properties();
        //sql配置文件名称
        String mysqlPropertiesName = "mysql.properties";
        try {
            //尝试使用当前类加载器读取properties文件
            InputStream inputStream = CandyJdbcC3p0Utils.class.getClassLoader().getResourceAsStream(mysqlPropertiesName);
            //确认输入流存在
            assert inputStream != null;
            //加载流 抛出IO异常
            properties.load(inputStream);
            //设置MySQL驱动
            c3p0Pool.setDriverClass(properties.getProperty("mysql.driver"));
            //设置MySQL链接URL服务器地址
            c3p0Pool.setJdbcUrl(String.format(properties.getProperty("mysql.url"),
                    System.getenv(properties.getProperty("mysql.host"))));
            //设置MySQL登录账户名
            c3p0Pool.setUser(System.getenv(properties.getProperty("mysql.user")));
            //设置MySQL登录账户密码
            c3p0Pool.setPassword(System.getenv(properties.getProperty("mysql.password")));
            //设置连接池初始化连接数 初始化链接4个 默认值3
            c3p0Pool.setInitialPoolSize(4);
            //设置连接池最小空闲连接数 最少空闲4个 默认值3
            c3p0Pool.setMinPoolSize(4);
            //设置连接池最大连接数 最大连接12个 默认值15
            c3p0Pool.setMaxPoolSize(12);
            //置连接池 数据连接不够时一次增长多少个 一次增加2个连接 默认值3
            c3p0Pool.setAcquireIncrement(2);
            //将设置好的数据库连接池保存
            dataSource = c3p0Pool;
            //其他参数的设置 https://blog.csdn.net/zhanghanlun/article/details/80918422
        } catch (IOException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return 返回MySQL连接对象
     * @throws SQLException 抛出sql异常
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /*
     * 使用默认的properties会自动注入
     * <p>
     * 类路径下提供一个c3p0.properties文件(不能改名)
     * c3p0.properties 文件
     * c3p0.JDBC.url=jdbc:mysql://localhost:3306/ms_cms?characterEncoding=utf8
     * c3p0.DriverClass=com.mysql.jdbc.Driver
     * c3p0.user=root
     * c3p0.pwd=
     * <p>
     * c3p0.acquireIncrement=3
     * c3p0.idleConnectionTestPeriod=60
     * c3p0.initialPoolSize=10
     * c3p0.maxIdleTime=60
     * c3p0.maxPoolSize=20
     * c3p0.maxStatements=100
     * c3p0.minPoolSize=5
     * <p>
     * 原文链接：https://blog.csdn.net/jdq928/article/details/84519141
     */
}
