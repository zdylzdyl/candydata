package cn.candy.basic;


import cn.candy.basic.utils.CandyJdbcC3p0Utils;
import cn.candy.basic.utils.CandyJdbcBasicUtils;
import cn.candy.basic.utils.CandyJdbcDruidUtils;
import cn.candy.basic.utils.CandyJdbcHikariUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.Random;


import static org.junit.Assert.assertTrue;

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
public class AppMySqlBasicTest {

    /**
     * 使用log4j记录日志
     * <p>
     * log4j写法
     */
    private static final Logger logger = Logger.getLogger(AppMySqlBasicTest.class);


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    /**
     * 0.01 测试从系统环境中读取变量
     */
    @Test
    public void checkSystemEnv() {
        //从系统环境中读取变量
        String[] envList = new String[]{"USERNAME", "MYSQL_USER", "MYSQL_PASSWORD", "MYSQL_HOST", "JAVA_HOME"};
        for (String s : envList) {
            //使用System.get env读取环境变量
            String env = System.getenv(s);
            //将不可用的信息打印
            if (env == null || "".equals(env)) {
                logger.error(String.format("System env [%s] get null.", s));
            }
        }
        //系统环境变量正常
        logger.debug(String.format("check System Environmental %s ok.", Arrays.toString(envList)));
    }

    /**
     * 测试mysql连接
     *
     * @throws SQLException 抛出sql异常
     */
    @Test
    public void testConnection2Mysql() throws SQLException {
        //获取连接执行测试查询
        logger.debug("mysql connection is " + (CandyJdbcBasicUtils.getConnection().
                prepareStatement("SELECT version()").executeQuery().next() ? "ok." : "error."));
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
            getResultSetInfo2Log(resultSet);
            //捕获异常
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 将查询结果集的内容打印到logger日志
     *
     * @param resultSet 查询结果集
     * @throws SQLException 抛出sql异常
     */
    private void getResultSetInfo2Log(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            StringBuilder stringBuilder = new StringBuilder("resultSet result:\r\n");
            //获取结果集的标题列等信息
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //获取一共有几列
            int numberOfColumns = resultSetMetaData.getColumnCount();
            //列数已知为numberOfColumns，使用for循环遍历
            for (int i = 0; i < numberOfColumns; i++) {
                //获取指定列的表的目录名称 mysql数据列从1开始
                stringBuilder.append(resultSetMetaData.getColumnName(i + 1)).append("\t");
            }
            stringBuilder.append("\r\n");
            //使用while获取位置数量的数据的值
            while (resultSet.next()) {
                for (int i = 0; i < numberOfColumns; i++) {
                    //获取指定列的表的值 mysql数据列从1开始
                    stringBuilder.append(resultSet.getObject(i + 1)).append("\t");
                }
                stringBuilder.append("\r\n");
            }
            logger.debug(stringBuilder);
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
     */
    @Test
    public void testCandyJdbcBasicUtils2Mysql() throws SQLException {
        //1.获取链接
        Connection connection = CandyJdbcBasicUtils.getConnection();
        //2.查询mysql版本 select @@version; select version();
        String sql = "SELECT version()";
        //3.获取可执行对象preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //4.执行查询
        ResultSet resultSet = preparedStatement.executeQuery();
        //5.如果查询的结果集不为空，进行详细值展示
        getResultSetInfo2Log(resultSet);
        //关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    /**
     * 测试普通连接的查询
     *
     * @throws SQLException 抛出sql异常
     */
    @Test
    public void test2MysqlQuery() throws SQLException {
        //1.查询mysql 语句
        String sql = "SELECT * from test_user where name like '%乔%'";
        //2.获取可执行对象preparedStatement执行查询
        getResultSetInfo2Log(CandyJdbcBasicUtils.getConnection().prepareStatement(sql).executeQuery());
    }

    /**
     * 测试普通连接的增删改查及事务
     *
     * @throws SQLException 抛出sql异常
     */
    @Test
    public void test2MysqlAdd() throws SQLException {
        //1.获得链接
        Connection connection = CandyJdbcBasicUtils.getConnection();
        //2.关闭自动提交事务，开启mysql事务
        connection.setAutoCommit(false);
        //3.mysql 增加语句
        String sql = "insert into test_user(name, phone) VALUE ('蛋糕',19907492847)";
        logger.debug(sql);
        //4.获取可执行对象preparedStatement执行查询
        int updateNumber = connection.prepareStatement(sql).executeUpdate();
        if (updateNumber > 0) {
            logger.debug("updateNumber: " + updateNumber);
        } else {
            logger.error("updateNumber: " + updateNumber);
        }
        //5.提交事务
        connection.commit();
        //关闭资源
        connection.close();
    }

    /**
     * 测试普通连接的增删改查及事务
     *
     * @throws SQLException 抛出sql异常
     */
    @Test
    public void test2MysqlDelete() throws SQLException {
        //1.获得链接
        Connection connection = CandyJdbcBasicUtils.getConnection();
        //2.关闭自动提交事务，开启mysql事务
        connection.setAutoCommit(false);
        //3.mysql 增加语句
        String sql = "delete from test_user where phone = 18807492847";
        logger.debug(String.format("query sql:[%s]", sql));
        //4.获取可执行对象preparedStatement执行查询
        int updateNumber = connection.prepareStatement(sql).executeUpdate();
        if (updateNumber > 0) {
            logger.debug("updateNumber: " + updateNumber);
        } else {
            logger.error("updateNumber: " + updateNumber);
        }
        //5.提交事务
        connection.commit();
        //关闭资源
        connection.close();
    }

    /**
     * 测试普通连接的增删改查及事务
     *
     * @throws SQLException 抛出sql异常
     */
    @Test
    public void test2MysqlUpdate() throws SQLException {
        //1.获得链接
        Connection connection = CandyJdbcBasicUtils.getConnection();
        //2.关闭自动提交事务，开启mysql事务
        connection.setAutoCommit(false);
        //3.mysql 增加语句
        String sql = "update test_user set name='更新测试元7' where phone = 18807492847";
        //4.获取可执行对象preparedStatement执行查询
        int updateNumber = connection.prepareStatement(sql).executeUpdate();
        String result = String.format("query sql:[%s],update number:[%s]", sql, updateNumber);
        if (updateNumber > 0) {
            logger.debug(result);
        } else {
            logger.error(result);
        }
        //5.提交事务
        connection.commit();
        //关闭资源
        connection.close();
    }

    /**
     * 获取查询结果 将结果输出到日志
     */
    private void quickGetResultSetInfo2Log(Connection connection, String sql) throws SQLException {
        //获取查询结果
        ResultSet executeAfterQuery = connection.prepareStatement(sql).executeQuery();
        //将结果输出到日志
        getResultSetInfo2Log(executeAfterQuery);
        executeAfterQuery.close();
    }

    /**
     * 测试普通连接的增删改查及事务 测试mysql的默认隔离级别 可重复读
     * 读同一个事务内未提交内容
     * 1.开启事务 进行查询
     * 2.增加一条数据 进行查询
     * 3.关闭事务 另开事务重新查询
     *
     * @throws SQLException 抛出sql异常
     */
    @Test
    public void test2MysqlAll() throws SQLException {
        //1.获得链接
        Connection connection = CandyJdbcBasicUtils.getConnection();
        //2.关闭自动提交事务，开启mysql事务
        connection.setAutoCommit(false);
        //进行查询
        String querySql = "SELECT * from test_user where name like '%蛋糕%'";
        //获取查询结果 将结果输出到日志
        quickGetResultSetInfo2Log(connection, querySql);
        //增加一条数据
        String insertSql = "insert into test_user(name, phone) VALUE ('蛋糕test',19927492847)";
        //获取可执行对象preparedStatement执行查询
        int updateNumber = connection.prepareStatement(insertSql).executeUpdate();
        String result = String.format("insert sql:[%s],insert number:[%s]", insertSql, updateNumber);
        if (updateNumber > 0) {
            logger.debug(result);
        } else {
            logger.error(result);
        }

        //获取查询结果 将结果输出到日志
        quickGetResultSetInfo2Log(connection, querySql);
        //提交事务
        connection.commit();
        //获取查询结果 将结果输出到日志
        quickGetResultSetInfo2Log(connection, querySql);
        //关闭资源
        connection.close();
    }

    /**
     * 测试c3p0连接池的连接
     */
    @Test
    public void testC3p0Connection() throws SQLException {
        //获取连接执行测试查询
        logger.debug("mysql connection is " + (CandyJdbcC3p0Utils.getConnection().
                prepareStatement("SELECT version()").executeQuery().next() ? "ok." : "error."));

    }

    /**
     * 测试c3p0连接的增删改查及事务 测试mysql的默认隔离级别 可重复读
     * 读同一个事务内未提交内容
     * 1.开启事务 进行查询
     * 2.增加一条数据 进行查询
     * 3.删除一条数据 进行查询
     * 3.关闭事务 另开事务重新查询
     *
     * @throws SQLException 抛出sql异常
     */
    @Test
    public void test2MysqlC3p0All() throws SQLException {
        //1.获得链接
        Connection connection = CandyJdbcC3p0Utils.getConnection();
        //2.关闭自动提交事务，开启mysql事务
        connection.setAutoCommit(false);
        //进行查询
        String querySql = "SELECT * from test_user where name like '%蛋糕%'";
        //获取查询结果 将结果输出到日志
        quickGetResultSetInfo2Log(connection, querySql);
        Long phoneNumber = new Random().nextInt(999999999) + 13000000000L;
        //增加一条数据
        String insertSql = String.format("insert into test_user(name, phone) VALUE ('蛋糕Test',%s)", phoneNumber);
        //获取可执行对象preparedStatement执行查询
        int updateNumber = connection.prepareStatement(insertSql).executeUpdate();
        String result = String.format("insert sql:[%s],insert number:[%s]", insertSql, updateNumber);
        if (updateNumber > 0) {
            logger.debug(result);
        } else {
            logger.error(result);
        }
        //获取查询结果 将结果输出到日志
        quickGetResultSetInfo2Log(connection, querySql);
        //删除一条数据
        String deleteSql = String.format("delete from test_user where phone = %s", phoneNumber);
        //获取可执行对象preparedStatement执行查询
        int deleteNumber = connection.prepareStatement(deleteSql).executeUpdate();
        result = String.format("insert sql:[%s],insert number:[%s]", deleteSql, deleteNumber);
        if (deleteNumber > 0) {
            logger.debug(result);
        } else {
            logger.error(result);
        }
        //获取查询结果 将结果输出到日志
        quickGetResultSetInfo2Log(connection, querySql);
        //提交事务
        connection.commit();
        //获取查询结果 将结果输出到日志
        quickGetResultSetInfo2Log(connection, querySql);
        //关闭资源
        connection.close();
    }

    /**
     * 测试druid连接池的连接
     */
    @Test
    public void testDruidConnection() throws SQLException {
        //获取连接执行测试查询
        logger.debug("mysql connection is " + (CandyJdbcDruidUtils.getConnection().
                prepareStatement("SELECT version()").executeQuery().next() ? "ok." : "error."));
    }

    /**
     * 测试druid连接池的连接
     */
    @Test
    public void testHikariCPConnection() throws SQLException {
        //获取连接执行测试查询
        logger.debug("mysql connection is " + (CandyJdbcHikariUtils.getConnection().
                prepareStatement("SELECT version()").executeQuery().next() ? "ok." : "error."));
    }
}
