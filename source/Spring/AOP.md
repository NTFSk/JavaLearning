
AOP 
===
___即 Aspect Oriented Program 面向切面编程___
`周边功能在Spring的面向切面编程AOP思想里，即被定义为切面`
AOP的思想里，核心业务和切面功能(周边功能)分别`独立进行开发`
然后把切面功能和核心业务功能"编织"在一起

[1.业务类](#1)
[2.日志切面](#2)
[3.核心配置applicationContext.xml](#3)
[4.运行效果](#4)


<h3 id="1">1.业务类</h3><br>

```java
public class ProductService {
     
    public void doSomeService(){
         
        System.out.println("doSomeService");
         
    }
     
}
```

___运行一下试试看___
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import com.how2java.service.ProductService;
 
public class TestSpring {
 
    public static void main(String[] args) {
    	// 读取配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
        // 通过关键字 s 获取对象，对象由spring生成
        ProductService s = (ProductService) context.getBean("s");
        // 调用对象的方法
        s.doSomeService();
    }
}
```
___输出结果：doSomeService___

<h3 id="2">2.日志切面</h3><br>

`Object object = joinPoint.proceed();`***就是将来与某个核心功能编织之后，用于执行核心功能的代码***
```java
package com.how2java.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
 
public class LoggerAspect {
 
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}
```
<h3 id="3">3.核心配置applicationContext.xml</h3><br>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context     
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
  
    <bean name="c" class="com.how2java.pojo.Category">
        <property name="name" value="yyy" />
    </bean>
 
    <bean name="p" class="com.how2java.pojo.Product">
        <property name="name" value="product1" />
        <property name="category" ref="c" />
    </bean>
    
    <!--声明业务对象-->
    <bean name="s" class="com.how2java.service.ProductService">
    </bean>   
    <!--声明日志切面-->
    <bean id="loggerAspect" class="com.how2java.aspect.LoggerAspect"/>
     
    <!--通过aop:config把业务对象和辅助功能编织在一起-->
    <aop:config>
    	<!--定义切入点名为loggerCutpoint-->
        <aop:pointcut id="loggerCutpoint"
            expression=
            "execution(* com.how2java.service.ProductService.*(..)) "/>
        <!--定义切面类-->
        <aop:aspect id="logAspect" ref="loggerAspect">
            <aop:around pointcut-ref="loggerCutpoint" method="log"/>
        </aop:aspect>
    </aop:config>    
  
</beans>
```
`execution(* com.how2java.service.ProductService.*(..)) `
表示对满足如下条件的方法调用，进行切面操作:
`* 返回任意类型`
`com.how2java.service.ProductService.* 包名以 com.how2java.service.ProductService 开头的类的任意方法`
`(..) 参数是任意数量和类型`


<h3 id="4">4.运行效果</h3><br>

```java
package com.how2java.test;
  
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import com.how2java.service.ProductService;
  
public class TestSpring {
  
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext.xml" });
        ProductService s = (ProductService) context.getBean("s");
        s.doSomeService();
    }
}
```