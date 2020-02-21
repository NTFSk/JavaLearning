### 基础的CRUD主要就是在对象的xml文件中写入不同的sql语句
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
 
    <mapper namespace="com.how2java.pojo">
        <insert id="addCategory" parameterType="Category" >
            insert into category_ ( name ) values (#{name})   
        </insert>
         
        <delete id="deleteCategory" parameterType="Category" >
            delete from category_ where id= #{id}  
        </delete>
        
        <!-- parameterType是指传入参数的类型，或者说要操纵的对象的类型-->
        <!-- resultType的类型是指得到的类型-->
        <select id="getCategory" parameterType="_int" resultType="Category">
            select * from   category_  where id= #{id}   
        </select>
 
        <update id="updateCategory" parameterType="Category" >
            update category_ set name=#{name} where id=#{id}   
        </update>
        <select id="listCategory" resultType="Category">
            select * from   category_     
        </select>    
    </mapper>
```

### 然后在程序中根据id调用相应的语句
```java
package com.how2java;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
 
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
 
import com.how2java.pojo.Category;
 
public class TestMybatis {
 
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
 
        Category c = new Category();
        c.setName("新增加的Category");
        session.insert("addCategory",c);
         
        listAll(session);
         
        session.commit();
        session.close();
 
    }
 
    private static void listAll(SqlSession session) {
        List<Category> cs = session.selectList("listCategory");
        for (Category c : cs) {
            System.out.println(c.getName());
        }
    }
}
```