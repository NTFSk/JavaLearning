:city_sunrise:在[完成Spring-mybatis整合](../Mybatis整合Spring-Mapper接口扫描)的基础上，再整合上SpringMVC 
---

[1.导入整合SpringMVC需要的包](#1)

[2.配置web.xml（重要）:star:](#2)

[3.配置spring-mvc.xml（SpringMVC的配置文件）](#3)

[4.编写Controller](#4)

[5.编写页面](#5)







<h3 id="1">1. 导入jar包</h3>



导入jar包
---

<h3 id="2">2. 配置web.xml（重要）</h3>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>01.mybatis</display-name>

	<!-- 配置SpringMVC编码过滤器  -->
	<filter>
		<filter-name>CharacterEncodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>utf-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>CharacterEncodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>


	<!-- Spring MVC servlet -->
    <!-- 配置前端控制器DispatcherServlet -->
	<servlet>
		<servlet-name>DispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<!-- 参数：读取spring-mvc.xml -->
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:spring-mvc.xml</param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>DispatcherServlet</servlet-name>
        <!-- 以.action结尾的 由 DispacherServlet 进行解析 -->
        <!-- 如果是/, 则所有的访问都由DispacherServlet进行解析 -->
		<url-pattern>*.action</url-pattern>
	</servlet-mapping>

  
  	<!-- 配置一个监听器用来启动spring -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	<!-- 修改路径 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:applicationContext.xml</param-value>
	</context-param>

  
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```



1)  启动Spring， 加载 applicationContext.xml

- 配置监听器启动Spring

  ```xml
  <!-- Spring监听器：启动Spring -->
  <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  <!-- listener默认加载的是WEB-INF目录下的applicationContext.xml文件 -->
  <!-- 所以需要修改一下路径，改为类路径下的applicationCOntext.xml文件 -->
  <!--spring和mybatis的配置文件-->
  <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpth:applicationContext.xml</param-value>
</context-param>
  ```
  
  

2） 启动SpringMVC, 加载 spring-mvc.xml

```xml
	<!-- 启动SpringMVC -->
	<!-- 通过SpringMVC提供的启动类来启动 -->
	<servlet>
		<servlet-name>DispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<!-- 参数: 读取spring-mvc.xml -->
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:spring-mvc.xml</param-value>
		</init-param>
	</servlet>
	
	<servlet-mapping>
		<servlet-name>DispatcherServlet</servlet-name>
		<url-pattern>*.action</url-pattern>
	</servlet-mapping>
```



<h3 id="3">3. 配置spring-mvc.xml</h3>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:contenxt="http://www.springframework.org/schema/context"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

	<!-- 自动扫描方式，使SpringMVC认为包下用了@Controller注解的类是控制器 -->
	<contenxt:component-scan base-package="cn.sm1234.controller"/>

	<!-- 注解驱动 -->
	<mvc:annotation-driven></mvc:annotation-driven>
	
	<!-- 视图解析器:简化在Controller类编写的视图路径 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<!-- 前缀 -->
		<property name="prefix" value="/WEB-INF/jsp/"/>
		<!-- 后缀 -->
		<property name="suffix" value=".jsp"/>
	</bean>

</beans>
```



<h3 id="4">4. 编写Controller</h3>

```java
package cn.sm1234.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@RequestMapping("/test")
	public String test(){
		return "test";
	}
}
```



<h3 id="5">5. 编写页面</h3>

```jsp
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    测试SpringMVC是否可用
  </body>
</html>

```







