注解方式AOP
---

[1.注解业务类](#1)<br>
[2.注解切面类](#2)<br>
[3.核心配置applicationContext.xml](#3)<br>
[4.运行结果](#4)<br>

<h3 id="1">1.注解业务类</h3><br>

***在类前面加上@Component("s")***
```java
package com.how2java.service;
 
import org.springframework.stereotype.Component;
 
@Component("s")
public class ProductService {
    public void doSomeService(){
        System.out.println("doSomeService");
    }
     
}
```
<h3 id="2">2.注解切面类</h3><br>

***在类前面加上@Aspect 和 @Component***<br>
`@Aspect注解表示这是一个切面`<br>
`@Component表示这是一个bean，由Spring进行管理`<br>

___在类中的方法前加上@Around(value = "execution(* com.how2java.service.ProductService.*(..))")___<br>
`表示对com.how2java.service.ProductService 这个类中的所有方法进行切面操作`<br>


```java
package com.how2java.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class LoggerAspect {
     
    @Around(value = "execution(* com.how2java.service.ProductService.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}
```

<h3 id="3">核心配置applicationContext.xml</h3><br>

***扫描包com.how2java.aspect 和 com.how2java.service, 定位业务类和切面类***<br>
`<context:component-scan base-package="com.how2java.service"/>`<br>
`<context:component-scan base-package="com.how2java.aspect"/>`<br>

***通过配置织入@Aspectj切面***<br>
`<aop:aspectj-autoproxy />`<br>

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
  	
  	<context:component-scan base-package="com.how2java.service"/>
    <context:component-scan base-package="com.how2java.aspect"/>
    <aop:aspectj-autoproxy/> 
   
</beans>
```
<h3 id="4">4.运行结果</h3><br>