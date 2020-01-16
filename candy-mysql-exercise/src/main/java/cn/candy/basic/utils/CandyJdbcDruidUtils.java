package cn.candy.basic.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * druid 数据库连接池
 *
 * @author : buxi
 * @date : 2020-01-16 15:01
 **/
public class CandyJdbcDruidUtils {
    /**
     * druid 数据库连接池
     */
    private static DataSource dataSource = null;

    static {
        DruidDataSource druidPool = new DruidDataSource();
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
            druidPool.setDriverClassName(properties.getProperty("mysql.driver"));
            //设置MySQL链接URL服务器地址
            druidPool.setUrl(String.format(properties.getProperty("mysql.url"),
                    System.getenv(properties.getProperty("mysql.host"))));
            //设置MySQL登录账户名
            druidPool.setUsername(System.getenv(properties.getProperty("mysql.user")));
            //设置MySQL登录账户密码
            druidPool.setPassword(System.getenv(properties.getProperty("mysql.password")));
            //设置连接池初始化连接数 初始化链接4个 默认值3
            druidPool.setInitialSize(4);
            //设置连接池最小空闲连接数 最少空闲4个 默认值3
            druidPool.setMinIdle(4);
            //设置连接池最大连接数 最大连接12个 默认值15
            druidPool.setMaxActive(12);

            //将设置好的数据库连接池保存
            dataSource = druidPool;
            //其他参数的设置 https://blog.csdn.net/zhanghanlun/article/details/80918422
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
