
一个简单的Spring MVC示例
---
#### 原理图

![](https://github.com/NTFSk/JavaLearning/blob/master/pictures/SSM/SpringMVC/SpringMVC小示例原理图.png)



[1.创建dynamic web project项目](#1)<br>
[2.导入jar包](#2)<br>
[3.配置入口Spring MVC的入口DispatcherServlet(web.xml)](#3)<br>
[4.映射配置文件(springmvc-servlet.xml)](#4)<br>
[5.控制类(IndexController)](#5)<br>
[6.视图(index.jsp)](#6)<br>


<h3 id="1">1.创建dynamic web project项目</h3><br>

![](https://stepimagewm.how2j.cn/1889.png)
<h3 id="2">2.导入jar包</h3><br>

![](https://stepimagewm.how2j.cn/1890.png)
<h3 id="3">3.配置入口Spring MVC的入口DispatcherServlet(web.xml)</h3><br>

___在WEB-INF目录下创建web.xml
	配置Spring MVC的入口, DispatcherServlet，把所有的请求都提交到该Servlet___
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.4" xmlns="http://java.sun.com/xml/ns/j2ee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee
http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>
            org.springframework.web.servlet.DispatcherServlet
        </servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

<h3 id="4">4.映射配置文件springmvc-servlet.xml</h3><br>

_`springmvc-servlet.xml` 与上一步中的<servlet-name>springmvc</servlet-name>`springmvc`对应_
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE beans PUBLIC "-//SPRING//DTD BEAN//EN" "http://www.springframework.org/dtd/spring-beans.dtd">
<beans>
    <bean id="simpleUrlHandlerMapping"
        class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
            	<!--通过访问/index 会交给id=indexController的bean处理-->
                <prop key="/index">indexController</prop>
            </props>
        </property>
    </bean>
    <!--id=indexController的bean配置为类：IndexController-->
    <bean id="indexController" class="controller.IndexController"></bean>
</beans>
```
<h3 id="5">5.控制类IndexController</h3><br>

_控制类 IndexController实现接口`Controller` ，提供方法`handleRequest`处理请求_
```java
package controller;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
 
public class IndexController implements Controller {
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 表示视图是 index.jsp
        ModelAndView mav = new ModelAndView("index.jsp");
        // 模型数据是message, 内容是 "Hello Spring MVC"
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
}
```


<h3 id="6">视图index.jsp</h3><br>

_通过`EL表达式`显示message的内容_

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<h1>${message}</h1>
```

