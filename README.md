# candyData

关于数据库JDBC的所有项目的练习

目前练习有
1. Mysql
- 为了方便包管理使用了Maven做依赖管理
- 由于用于测试mysql的基本方法，于是代码编写在Junit测试单元中，主要为了熟悉基础的JDBC写法
- 测试基本的CRUD方法，及事务的使用，验证mysql默认事务隔离级别:不可重复读
- 入门级使用c3p0,druid,HikariCP三种数据库连接池

2. Redis


3. oracle

all databases description

基础配置连接池
    有哪些连接池 为什么使用 历史？druid与HikariCP等数据库连接池的优缺点 如何选择
框架层
一共有那些框架 特点及使用场景 mybatis 的各种用法
    - 使用mybatis操作数据库
      - 使用实体类开发
      - 使用Dao开发
      - 使用Mapper动态代理开发
      - 附加连接池优化
与web框架的整合 spring springMVC 
    - 使用spring Ioc管理创建的链接对象
    - 使用spring Aop处理管理的事务
    - 与tomcat整合
    - 与spring整合
    - 与springMVC整合

业务层 
1. 增删改查
2. 多表联查
- 单表
- 一对一
- 一对多
  - 外键链接
  - union联查
- 多对多
  - 外键链接
  - union联查


