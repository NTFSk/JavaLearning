
## 对行为的注解
[1.核心配置文件applicationContext.xml](#1)<br>

[2.配置pojo类](#2)<br>


<h3 id="1">1. 核心配置文件applicationContext.xml</h3><br>

#### 在文件中加入一行"<context:annotation-config/>"

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx" xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context     
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
  
    <context:annotation-config/>
    <bean name="c" class="com.how2java.pojo.Category">
        <property name="name" value="category 1" />
    </bean>
    <bean name="p" class="com.how2java.pojo.Product">
        <property name="name" value="product1" />
    </bean>
  
</beans>
```

<h3 id="2">2. 配置pojo类</h3><br>

#### 方法1： 在Product.java的category属性前加上@Autowired注解

```java

    private Category category;
```

#### 方法1.2：在setCategory方法前加上@Autowired

```java
@Autowired
public void setCategory(Category category) {
        this.category = category;
    }
```

#### 方法2：@Resource

***这里的name="c"就关联了核心配置中name="c"的bean***
```java
@Resource(name="c")
    private Category category;
```


## 对Bean的注解
[1.核心配置文件applicationContext.xml](#3)<br>

[2.配置pojo类](#4)<br>

[3.测试结果](#5)<br>

<h3 id="3">applicationContext.xml</h3><br>

在applicationContext.xml中新增`<context:component-scan base-package="com.how2java.pojo"/>`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx" xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
   http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context     
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">
  
    <context:component-scan base-package="com.how2java.pojo"/>
     
</beans>
```


<h3 id="4">pojo类</h3><br>

#### 1.在类之前加上@Component注解，表明此类是bean
```java
@Component("p")
public class Product {

-------------------

@Component("c")
public class Category {
```

#### 2.因为配置从applicationContext.xml中移除了，所以在类中进行属性初始化
```java
private int id;
private String name="product 1";
```

<h3 id="5">测试</h3><br>

```java
package com.how2java.test;
 
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import com.how2java.pojo.Product;
 
public class TestSpring {
 
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
        Product p = (Product) context.getBean("p");
        System.out.println(p.getName());
        System.out.println(p.getCategory().getName());
    }
}
```

***输出：product1
		category1***
