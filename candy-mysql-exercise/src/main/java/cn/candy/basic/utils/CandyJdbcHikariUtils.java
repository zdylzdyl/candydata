package cn.candy.basic.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * HikariCP 数据库连接池工具
 * 参考链接：https://blog.csdn.net/ssxueyi/article/details/83505322
 *
 * @author : buxi
 * @date : 2020-01-16 15:56
 **/
public class CandyJdbcHikariUtils {
    /**
     * HikariCP 数据库连接池
     */
    private static DataSource dataSource = null;

    static {
        //配置文件
        HikariConfig hikariConfig = new HikariConfig();
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
            hikariConfig.setDriverClassName(properties.getProperty("mysql.driver"));
            //设置MySQL链接URL服务器地址
            hikariConfig.setJdbcUrl(String.format(properties.getProperty("mysql.url"),
                    System.getenv(properties.getProperty("mysql.host"))));
            //设置MySQL登录账户名
            hikariConfig.setUsername(System.getenv(properties.getProperty("mysql.user")));
            //设置MySQL登录账户密码
            hikariConfig.setPassword(System.getenv(properties.getProperty("mysql.password")));
            //设置连接池 测试语句
            hikariConfig.setConnectionTestQuery("SELECT version()");
            //将设置好的数据库连接池保存
            dataSource = new HikariDataSource(hikariConfig);
            //其他参数的设置 https://blog.csdn.net/ssxueyi/article/details/83505322
        } catch (IOException e) {
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
}
