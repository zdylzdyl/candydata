# candyData

关于数据库JDBC的所有项目的练习

目前练习有
1. Mysql
- 为了方便包管理使用了Maven做依赖管理
- 由于用于测试mysql的基本方法，于是代码编写在Junit测试单元中，主要为了熟悉基础的JDBC写法

2. Redis

all databases description



CURD(全Maven处理依赖)


- 使用数据库连接池(Druid/c3p0)操作数据库，优化链接
- 使用mybatis操作数据库
  - 使用实体类开发
  - 使用Dao开发
  - 使用Mapper动态代理开发
  - 附加连接池优化
- 使用spring Ioc管理创建的链接对象
- 使用spring Aop处理管理的事务
- 与tomcat整合
- 与spring整合
- 与springMVC整合
- 与git同步
- 单表
- 一对一
- 一对多
  - 外键链接
  - union联查
- 多对多
  - 外键链接
  - union联查


