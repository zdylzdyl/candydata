package cn.candy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Mysql的简单操作，将代码写在测试类中，方便测试
 * Unit test for simple App.
 * <p>
 * 1.直接连接Mysql数据库的操作
 * 2.使用JDBC封装进行操作
 * 3.使用MySQL数据库连接池进行操作
 * <p><p>1.使用c3p0
 * <p><p>2.使用druid
 * <p><p>3.使用hikariCP
 *
 * @author buxi
 * @date 2020-01-15
 */
@SuppressWarnings("unused")
public class AppMySqlTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    /**
     * 1.1直接链接操作Mysql数据库
     */
    private void simpleConnection2Mysql() {
        try {
            //1.加载驱动 方法两种
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取数据库链接 getConnection构造方法三种 数据值推荐使用properties读取配置文件
            Connection connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:3306/candy?useUnicode=true&characterEncoding=utf-8&useSSL=false", System.getenv("MYSQL_HOST")),
                    System.getenv("MYSQL_USER"), System.getenv("MYSQL_PASSWORD"));//执行的SQL语句
            String sql = "SELECT * FROM test_user LIMIT ?,?";
            //3.获取可执行对象preparedStatement 方法两种，无sql注入也可以使用statement sql语句使用增删改查
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //4.设置preparedStatement参数 无参数可以略过 此处为分页
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 10);
            //5.获取查询的结果集 主要方法三种 execute() executeUpdate() executeQuery() 等
            ResultSet resultSet = preparedStatement.executeQuery();
            //如果查询的结果集不为空，进行详细值展示
            if (resultSet != null) {
                //获取结果集的标题列等信息
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                //获取一共有几列
                int numberOfColumns = resultSetMetaData.getColumnCount();
                //列数已知为numberOfColumns，使用for循环遍历
                for (int i = 0; i < numberOfColumns; i++) {
                    //获取指定列的表的目录名称 mysql数据列从1开始
                    System.out.print(resultSetMetaData.getColumnName(i + 1) + "\t");
                }
                //换行
                System.out.println();
                //使用while获取位置数量的数据的值
                while (resultSet.next()) {
                    for (int i = 0; i < numberOfColumns; i++) {
                        //获取指定列的表的值 mysql数据列从1开始
                        System.out.print(resultSet.getObject(i + 1) + "\t");
                    }
                    //换行
                    System.out.println();
                }
            }
            //捕获异常
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1.2测试 直接链接操作Mysql数据库 的操作
     */
    @Test
    public void testSimpleConnection2Mysql() {
        simpleConnection2Mysql();
    }

    /**
     * 2.1使用jdbcUtils工具类进行链接数据库
     * 本质上还是直接链接数据库 但是封装了 获取链接及释放链接资源
     */
    private static class CandyJdbcUtils {
        //mysql远程服务器
        private static String url;
        //mysql远程用户名
        private static String user;
        //mysql远程用户密码
        private static String password;
        //mysql驱动
        private static String driver;

        //使用静态代码块初始化类
        static {
            //sql配置文件名称
            String mysqlPropertiesName = "mysql.properties";
            //1.创建Properties对象 使用properties读取配置
            Properties properties = new Properties();
            //2.尝试使用当前类加载器读取properties文件
            InputStream inputStream = CandyJdbcUtils.class.getClassLoader().getResourceAsStream(mysqlPropertiesName);
            try {
                //确认输入流存在
                assert inputStream != null;
                //3.从文件流中加载properties配置文件
                properties.load(inputStream);
                //4.读取properties配置
                url = String.format(properties.getProperty("url"), System.getenv("MYSQL_HOST"));
                user = /*properties.getProperty("user");*/System.getenv("MYSQL_USER");
                password = /*properties.getProperty("password");*/System.getenv("MYSQL_PASSWORD");
                driver = properties.getProperty("driver");
                try {
                    //5.加载驱动
                    Class.forName(driver);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("使用properties配置,读取driver加载失败.尝试读取文件为:" + mysqlPropertiesName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("无法加载" + mysqlPropertiesName + "配置文件,无法从properties中加载配置");
            }
        }

        /**
         * 获得Connection链接
         *
         * @return Connection sql链接
         */
        public static Connection getConnection() {
            //创建connection引用
            Connection connection = null;
            try {
                //获得connection链接对象
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("创建Connection链接失败.mysql driver:\t" + driver);
            }
            return connection;
        }

        /**
         * 关闭资源/链接 Statement Connection
         *
         * @param connection sql链接
         * @param statement  执行语句
         */
        public static void close(Connection connection, Statement statement) {
            if (statement != null) {
                try {
                    //关闭statement
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    //关闭connection
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 关闭资源/链接 ResultSet Statement Connection - 重载
         *
         * @param connection sql链接
         * @param statement  可执行语句 兼容prepareStatement
         * @param resultSet  查询结果集
         */
        public static void close(Connection connection, Statement statement, ResultSet resultSet) {
            if (resultSet != null) {
                try {
                    //关闭resultSet
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //调用已有方法 关闭资源
            close(connection, statement);
        }
    }

    /**
     * 2.1使用jdbcUtils工具类进行链接数据库
     */
    @Test
    public void testCandyJdbcUtils2Mysql() throws SQLException {
        //1.获取链接
        Connection connection = CandyJdbcUtils.getConnection();
        //2.查询mysql版本 select @@version; select version();
        String sql = "SELECT version()";
        //3.获取可执行对象preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //4.执行查询
        ResultSet resultSet = preparedStatement.executeQuery();
        //5.如果查询的结果集不为空，进行详细值展示
        if (resultSet != null) {
            //获取结果集的标题列等信息
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //获取一共有几列
            int numberOfColumns = resultSetMetaData.getColumnCount();
            //列数已知为numberOfColumns，使用for循环遍历
            for (int i = 0; i < numberOfColumns; i++) {
                //获取指定列的表的目录名称 mysql数据列从1开始
                System.out.print(resultSetMetaData.getColumnName(i + 1) + "\t");
            }
            //换行
            System.out.println();
            //使用while获取位置数量的数据的值
            while (resultSet.next()) {
                for (int i = 0; i < numberOfColumns; i++) {
                    //获取指定列的表的值 mysql数据列从1开始
                    System.out.print(resultSet.getObject(i + 1) + "\t");
                }
                //换行
                System.out.println();
            }
        }
    }

    /**
     * 测试从系统环境中读取变量
     */
    @Test
    public void checkSystemEnv() {
        //从系统环境中读取变量
        String[] envList = new String[]{"USERNAME", "MYSQL_USER", "MYSQL_PASSWORD", "MYSQL_HOST", "JAVA_HOME"};
        for (String s : envList) {
            String env = System.getenv(s);
            //将不可用的信息打印 并报错
            if (env == null || "".equals(env)) {
                System.out.println(String.format("System env [%s] get null.", s));
                fail(String.format("System env [%s] get null.", s));
            }

        }
    }
}
