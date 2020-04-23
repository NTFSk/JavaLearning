Mybatis整合Spring-加入Spring事务管理
===

首先在[Mybatis整合Spring-Mapper接口扫描](../Mybatis整合Spring-Mapper接口扫描)项目的基础上，开发出一个业务类，
功能是添加用户

```java
package cn.sm1234.service;

import cn.sm1234.domain.Customer;

// 这是一个业务类的接口
public interface CustomerService {
	
	public void saveCustomer(Customer customer);
}
```



```java
package cn.sm1234.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;
import cn.sm1234.service.CustomerService;

// 业务实现类
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	// 注入Mapper对象
	@Resource
	private CustomerMapper customerMapper;
	
	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerMapper.saveCustomer(customer);
		
		//模拟异常
		int i = 100/0;
		customerMapper.saveCustomer(customer);
	}

}

```

`@Service("customerService")`的作用：
---

因为我们在__配置文件__中添加了如下代码:

```xml
<!-- 开启Spring的IOC注解的扫描 -->
<context:component-scan base-package="cn.sm1234"></context:component-scan>
```

所以Spring将自动扫描`cn.sm1234`路径下的包

如果扫描到了一个类带了`@Service`注解，将自动注册到Spring容器，不需要再在`applicationContext.xml`配置文件中定义bean了

也就是说相当于在`applicationContext.xml`配置文件里配置如下信息：

```
 <bean id="customerService"
     class="CustomerServiceImpl">
      ......    
 </bean>
 
```

_也可以把`@Service`注解理解成给类取了一个别名_

## `@Transactional `注解是用来实现事务的

`@Transactional`注解管理事务的实现步骤:

1. 在`applicationContext.xml`配置文件中添加事务配置信息

   ```xml
   <!-- 开启Spring的事务 -->
   	<!-- 事务管理器 -->
   	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
   		<property name="dataSource" ref="dataSource"/>
   	</bean>
   	<!-- 启用Spring事务注解 -->
   	<tx:annotation-driven transaction-manager="transactionManager"/>
   
   ```

   

2. 将`@Transactional `注解添加到合适的方法上，并设置合适的属性信息

   @Transactional 注解的属性信息如下表所示

| 属性名           | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| name             | 当在配置文件中有多个 TransactionManager , 可以用该属性指定选择哪个事务管理器。 |
| propagation      | 事务的传播行为，默认值为 REQUIRED。                          |
| isolation        | 事务的隔离度，默认值采用 DEFAULT。                           |
| timeout          | 事务的超时时间，默认值为-1。如果超过该时间限制但事务还没有完成，则自动回滚事务。 |
| read-only        | 指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。 |
| rollback-for     | 用于指定能够触发事务回滚的异常类型，如果有多个异常类型需要指定，各类型之间可以通过逗号分隔。 |
| no-rollback- for | 抛出 no-rollback-for 指定的异常类型，不回滚事务。            |

然后看一下测试类

```java
package cn.sm1234.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sm1234.domain.Customer;
import cn.sm1234.service.CustomerService;

public class MybatisSpringTest {
	
	@Test
	public void test() {
		// 1.加载spring配置
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		CustomerService customerService = (CustomerService)ac.getBean("customerService");
		
		Customer customer = new Customer();
		customer.setName("事务");
		customer.setGender("男");
		customer.setTelephone("123123");
		customer.setAddress("厦门");
		
		customerService.saveCustomer(customer);
	}
}

```

在业务类中的异常存在的情况下，是不会插入数据的



---

完整的`applicationContext.xml`

- 数据源配置（链接数据库）
- SqlSessionFactoryBean配置（关联连接池，加载sql映射文件）
- MapperScannerConfigurer配置（扫描mapper接口所在的包路径）

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
	
	<!-- 
		如果采用Mapper接口包扫描的方式，那么每个Mapper接口在Spring容器中的id名称，是它的类名:
		例如 CustomerMapper  ->  customerMapper
	 -->
	<!-- Mapper接口的扫描 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 配置Mapper接口所在的包路径 -->
		<property name="basePackage" value="cn.sm1234.dao"></property>
	</bean>
	
	<!-- 开启Spring的IOC注解的扫描 -->
	<context:component-scan base-package="cn.sm1234"></context:component-scan>
	
	<!-- 开启Spring的事务 -->
	<!-- 事务管理器 -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>
	<!-- 启用Spring事务注解 -->
	<tx:annotation-driven transaction-manager="transactionManager"/>

</beans>
```

