整合spring与mybatis
---

整合spring与myabtis重点在于Spring的核心配置文件：__applicationContext.xml__

[1. pojo](#1)

[2. Mapper接口](#2)

[3. Mapper的xml配置文件](#3)

[4. applicationContext.xml（重要）:star:](#4)

[5. 测试](#5)

---

<h3 id="1">1. Category.java</h3>

```java
package com.how2java.pojo;
 
public class Category {
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
     
}
```



<h3 id="2">2. CategoryMapper.java</h3>

```java
package com.how2java.mapper;
  
import java.util.List;
 
import com.how2java.pojo.Category;
  
public interface CategoryMapper {
  
    public int add(Category category); 
        
    public void delete(int id); 
        
    public Category get(int id); 
      
    public int update(Category category);  
        
    public List<Category> list();
     
    public int count(); 
     
}
```



<h3 id="3">3. CategoryMapper.xml</h3>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
 
    <mapper namespace="com.how2java.mapper.CategoryMapper">
        <insert id="add" parameterType="Category" >
            insert into category_ ( name ) values (#{name})   
        </insert>
         
        <delete id="delete" parameterType="Category" >
            delete from category_ where id= #{id}  
        </delete>
         
        <select id="get" parameterType="_int" resultType="Category">
            select * from   category_  where id= #{id}   
        </select>
 
        <update id="update" parameterType="Category" >
            update category_ set name=#{name} where id=#{id}   
        </update>
        <select id="list" resultType="Category">
            select * from   category_     
        </select>    
    </mapper>
```



<h3 id="4">4. applicationContext.xml（重要）</h3>

在这个配置文件中我们大体要完成以下三个配置：

- 数据源配置
- SqlSessionFactoryBean配置
- MapperScannerConfigurer配置



```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx" xmlns:jdbc="http://www.springframework.org/schema/jdbc"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xsi:schemaLocation="
     http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
     http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
     http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-3.0.xsd
     http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
     http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
     http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    
	<!-- 用于激活注解,让我们可以使用@Autowired、@Resource、@Required等注解 -->
   <context:annotation-config />
    
    <!--数据源配置:采用Spring默认的DriverManagerDataSource -->
    <!-- 配置连接数据库的驱动，URL，账号和密码 -->
   <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource"> 
        <property name="driverClassName"> 
            <value>com.mysql.jdbc.Driver</value> 
        </property> 
        <property name="url"> 
            <value>jdbc:mysql://localhost:3306/how2java?characterEncoding=UTF-8</value> 
        </property> 
        <property name="username"> 
            <value>root</value> 
        </property> 
        <property name="password"> 
            <value>admin</value> 
        </property>  
   </bean>
     
    <!-- SqlSessionFactoryBean配置 -->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="typeAliasesPackage" value="com.how2java.pojo" />
        <!-- 关联连接池 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 扫描xml配置文件-->
        <property name="mapperLocations" value="classpath:com/how2java/mapper/*.xml"/>
    </bean>
 
    <!-- 自动扫描包下的所有Mapper  -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.how2java.mapper"/>
    </bean>
     
</beans>
```



<h3 id="5">5. 测试类</h3>

```java
package com.java.test;
 
import java.util.List;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import com.how2java.mapper.CategoryMapper;
import com.how2java.pojo.Category;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MybatisTest {
 
    @Autowired
    private CategoryMapper categoryMapper;
 
    @Test
    public void testAdd() {
        Category category = new Category();
        category.setName("new Category");
        categoryMapper.add(category);
    }
 
    @Test
    public void testList() {
        System.out.println(categoryMapper);
        List<Category> cs=categoryMapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
        }
    }
 
}
```

