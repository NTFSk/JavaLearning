把[去掉Mapper实现类](../Mybatis整合Spring-去掉Mapper实现类)的项目升级成使用Mapper接口扫描的方式（推荐）
===

[1.导入包](#1)

[2.创建实体类](#2)

[3.创建Mapper接口](#3)

[4.配置applicationContext.xml​（重要）:star:](#4)

[5.编写测试类](#5)

<h3 id="1">1. 导入包</h3>



<h3 id="2">2. 实体类</h3>

```java
package cn.sm1234.domain;

public class Customer {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private String name;
	private String gender;
	private String telephone;
	private String address;
}

```



<h3 id="3">3. Mapper接口</h3>

```java
package cn.sm1234.dao;

import cn.sm1234.domain.Customer;

public interface CustomerMapper {
	/**
	 * 添加一个客户
	 */
	public void saveCustomer(Customer customer);
}

```

<h3 id="4">4. 配置applicationContext.xml（重要）</h3>

在`applicationContext.xml`的同一个包下，创建一个`jdbc.properties`文件，将数据库的信息写进去，在applicationContext.xml中调用

```java
jdbc.url=jdbc:mysql://localhost:3306/ssm?useUnicode=true&characterEncoding=utf8
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.user=root
jdbc.password=123123
```



```xml
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
	
	<!-- 创建数据源DataSource(连接池对象) -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <!--这里的属性就是连接数据库所需要的参数以及连接池的一些基本配置-->
		<property name="url" value="${jdbc.url}"/>
		<property name="driverClassName" value="${jdbc.driverClass}"/>
		<property name="username" value="${jdbc.user}"/>
		<property name="password" value="${jdbc.password}"/>
        <property name="maxActive" value="10" />
		<property name="maxIdle" value="5" />
	</bean>
	
	<!-- 创建SqlSessionFactory对象 -->
    <!--创建该对象的目的是通过该对象拿到一个sqlSession对象，然后通过sqlSession对象执行数据库操作-->
    <!-- 有了该配置，就不需要Mybatis的配置文件了，完美做到Spring和myabtis的整合-->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 关联连接池 -->
		<property name="dataSource" ref="dataSource"></property>
		<!-- 加载mapper接口的sql映射文件 -->
        <!-- 类路径下的mapper包下的xml文件 -->
		<property name="mapperLocations" value="classpath:mapper/*.xml"/>
	</bean>
	
	<!-- 
		如果采用Mapper接口包扫描的方式，那么每个Mapper接口在Spring容器中的id名称，是它的类名:
		例如 CustomerMapper  ->  customerMapper
	 -->
	<!-- Mapper接口的扫描 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 配置Mapper接口所在的包路径 -->
		<property name="basePackage" value="cn.sm1234.dao"></property>
	</bean>
	

</beans>
```

* __Mapper接口包扫描的作用是把每个Mapper接口和其对应的xml文件关联起来__

  

<h3 id="5">5. 测试类</h3>

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
		customer.setName("测试3");
		customer.setGender("男");
		customer.setTelephone("123123");
		customer.setAddress("厦门");
		
		customerMapper.saveCustomer(customer);
	}
}

```

