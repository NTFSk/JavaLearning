
[1.Mapper接口](#1)<br>
[2.核心配置(mybatis-config.xml)](#2)<br>
[3.运行](#3)<br>


<h2 id="1">1.Mapper接口</h2><br>

___MyBatis 3 推荐使用 Mapper接口的方式来执行 xml 配置中的 SQL，用起来很方便，也很灵活___<br>


```java
package com.how2java.mapper;
  
import java.util.List;
 
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
 
import com.how2java.pojo.Category;
  
public interface CategoryMapper {
  
    @Insert(" insert into category_ ( name ) values (#{name}) ") 
    public int add(Category category); 
        
    @Delete(" delete from category_ where id= #{id} ") 
    public void delete(int id); 
        
    @Select("select * from category_ where id= #{id} ") 
    public Category get(int id); 
      
    @Update("update category_ set name=#{name} where id=#{id} ") 
    public int update(Category category);  
        
    @Select(" select * from category_ ") 
    public List<Category> list(); 
}
```

__对比xml方式__

```xml
<insert id="addCategory" parameterType="Category" >
    insert into category_ ( name ) values (#{name})   
</insert>
 
<delete id="deleteCategory" parameterType="Category" >
    delete from category_ where id= #{id}  
</delete>
```
__发现注解方式的CRUD就是把 xml 中的 sql 语句注解到接口中声明的方法上__

---
<h2 id="2">2.核心配置(mybatis-config.xml)</h2><br>

__在核心配置中添加该接口的映射__
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <typeAliases>
      <package name="com.how2java.pojo"/>
    </typeAliases>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
            <property name="driver" value="com.mysql.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost:3306/how2java?characterEncoding=UTF-8"/>
            <property name="username" value="root"/>
            <property name="password" value="admin"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="com/how2java/pojo/Category.xml"/>
        ```
        ```xml
        <mapper class="com.how2java.mapper.CategoryMapper"/> 
        ```
        ```
    </mappers>
</configuration>
```

---
<h2 id="3">3.编写测试类进行测试</h2><br>

___通常是通过 xml 配置文件来创建SqlSessionFactory对象，然后再获取SqlSession对象，接着获取自定义的 Mapper 接口的代理对象，最后调用接口方法___
```java
    // 读取核心配置文件
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    // 获取session对象
    SqlSession session = sqlSessionFactory.openSession();
    // 获取接口对象
    CategoryMapper mapper = session.getMapper(CategoryMapper.class);
```

   __再利用接口对象实现操作__

```java
    // 增加一条数据
    Category c= mapper.get(8);
    c.setName("修改了的Category名稱");
    mapper.update(c);

    // 遍历并输出所有对象的name属性
    List<Category> cs = mapper.list();
        for (Category c : cs) {
            System.out.println(c.getName());
        }
```

---
