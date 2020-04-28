学生信息管理系统
===



## 系统环境

|  软件   |   版本   |
| :-----: | :------: |
|   jdk   | j2SE-1.5 |
| Spring  |  4.3.9   |
| Mybatis |  3.4.4   |
|  c3p0   |  0.9.1   |
|  MySQL  |   5.7    |
| Tomcat  |   9.0    |





## 项目结构



![image-20200424184443291](包的结构.png)

* controller 控制类所在的包，controller层是连接前端和后端的

  > controller接受前端发来的请求，同时向后端发送请求
  >
  > ——>service 层
  >
  > ——>serviceImpl实现service层,同时连接dao层
  >
  > ——>通过dao层去实现对数据库的操作（在dao层中同样是接口）
  >
  > ——>在XML文件中通过namespace完成连接dao层。

* dao 与数据库打交道的层，需要与mapper配置文件结合使用

* entity 实体类所在的包，数据库中的每张表都对应一个实体类

* service 业务类，声明实体有哪些方法

* service.impl 业务实现类，实体操作的具体实现（通过Dao层接口的实体进行实现数据库操作）

* util 其它类



