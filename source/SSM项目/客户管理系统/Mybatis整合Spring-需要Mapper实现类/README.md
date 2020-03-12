Mybatis整合Spring-需要Mapper实现类
===

___这种方案实现起来比较的麻烦，只需要了解一下就好___ :sweat_smile:

[1. 导入包](#1)

[2. 编写Mapper实现类](#2)

​	- Mapper实现类的功能是通过继承Spring提供的SqlSessionDaoSupport类得到一个SqlSession对象，然后通过该对象实现一些操作

[3. 编写 applicationContext.xml（Spring核心配置）](#3)

[4. 测试类](#4)

<h3 id="1">1. 导入包</h3>

[要导入的包](./要导入的包)

<h3 id="2">2. 编写Mapper实现类</h3>

```java
package cn.sm1234.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

// CustomerMapper的实现类去继承Spring提供的SqlSessionDaoSupport
// 用其里面的getSqlSession()方法可以很方便的拿到一个SqlSession的对象
public class CustomerMapperImpl extends SqlSessionDaoSupport implements CustomerMapper {

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.getSqlSession();
		// 拿到sqlSession后，就可以做一些我们想做的操作
		// 比如增加对象
		sqlSession.insert("saveCustomer",customer);
		/*
		 * 这里不需要进行事务的提交
		 */
	}

}

```

_上面的“savaCustomer”对应的sql语句：_

```xml
<!-- 添加客户 -->
	<insert id="saveCustomer" parameterType="cn.sm1234.domain.Customer">
		INSERT INTO ssm.t_customer 
			( 
			NAME, 
			gender, 
			telephone, 
			address
			)
			VALUES
			( 
			#{name}, 
			#{gender}, 
			#{telephone}, 
			#{address}
			)
	</insert>
```

<h3 id="3">3. applicationContext.xml（Spring核心配置）</h3>

```java
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
	
	
	<!-- 读取jdbc.properties -->
	<context:property-placeholder location="classpath:jdbc.properties"/>
	
	<!-- 创建DataSource(连接池对象) -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="url" value="${jdbc.url}"/>
		<property name="driverClassName" value="${jdbc.driverClass}"/>
		<property name="username" value="${jdbc.user}"/>
		<property name="password" value="${jdbc.password}"/>
	</bean>
	
	<!-- 创建SqlSessionFactory对象 -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 关联连接池 -->
		<property name="dataSource" ref="dataSource"></property>
		<!-- 加载sql映射文件 -->
		<property name="mapperLocations" value="classpath:mapper/*.xml"/>
	</bean>
	
	
	<!-- 创建CustomerMapperImpl对象，注入SqlSessionFactory -->
	<bean id="customerMapper" class="cn.sm1234.dao.impl.CustomerMapperImpl">
		<!-- 关联SqlSessionFactory -->
		<property name="sqlSessionFactory" ref="sqlSessionFactory"/>
	</bean>
	
</beans>
```

创建连接池的操作需要借助jdbc.properties文件:

```java
jdbc.url=jdbc:mysql://localhost:3306/ssm?useUnicode=true&characterEncoding=utf8
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.user=root
jdbc.password=123123
```



<h3 id="4">4. 测试类</h3>

```java
package cn.sm1234.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

public class MybatisSpringTest {
	
	@Test
	public void test() {
		// 1.加载spring配置
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 2.获取对象
		CustomerMapper customerMapper = (CustomerMapper)ac.getBean("customerMapper");
		// 3.调用方法
		Customer customer = new Customer();
		customer.setName("lisi");
		customer.setGender("男");
		customer.setTelephone("123123");
		customer.setAddress("厦门");
		
		customerMapper.saveCustomer(customer);
	}
}

```

