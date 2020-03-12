Mybatis整合Spring-去掉Mapper实现类
===

相较于[需要实现Mapper实现类](../Mybatis整合Spring-需要Mapper实现类)的整合方式
该方式只需要删掉Mapper实现类
然后修改applicationContext.xml文件

去掉之间Mapper实现类的配置，添加Mapper的配置

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop" 
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
	http://www.springframework.org/schema/beans/spring-beans.xsd
	http://www.springframework.org/schema/context
	http://www.springframework.org/schema/context/spring-context.xsd
	http://www.springframework.org/schema/aop
	http://www.springframework.org/schema/aop/spring-aop.xsd
	http://www.springframework.org/schema/tx 
	http://www.springframework.org/schema/tx/spring-tx.xsd">
	

<!-- 这是Spring的核心配置  -->


​	

<!-- 读取jdbc.properties -->
<context:property-placeholder location="classpath:jdbc.properties"/>

<!-- 创建DataSource(连接池对象) -->
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
	<property name="url" value="${jdbc.url}"/>
	<property name="driverClassName" value="${jdbc.driverClass}"/>
	<property name="username" value="${jdbc.user}"/>
	<property name="password" value="${jdbc.password}"/>

</bean>

<!-- spring和mybaits整合之后的配置文件,一般以这种方式实现 SqlSessionFactory的创建:-->
<!-- SqlSessionFactory是创建SqlSession的工厂 -->
<!-- SqlSession实例用来直接执行被映射的SQL语句 -->

<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
	<!-- 关联连接池 -->
	<property name="dataSource" ref="dataSource"></property>
	<!-- 加载sql映射文件 -->
	<property name="mapperLocations" value="classpath:mapper/*.xml"/>
</bean>

```xml
<!-- 配置Mapper接口 -->
<bean id="customerMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
	<!-- 关联Mapper接口 -->
	<property name="mapperInterface" value="cn.sm1234.dao.CustomerMapper"/>
	<!-- 关联SqlSessionFactory -->
	<property name="sqlSessionFactory" ref="sqlSessionFactory"/>
</bean>
```

</beans>