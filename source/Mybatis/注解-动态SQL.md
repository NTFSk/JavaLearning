用注解的方式实现动态SQL
===

[1.首先准备一个类,用`SQL类`的方式提供对应的SQL语句](#1)
[2.Mapper接口-把接口上的sql语句注解,改为引用类的方法](#2)


<h2 id="1">新增CategoryDynaSqlProvider，提供CRUD对应的SQL语句</h2><br>

```java
package com.how2java;
 
import org.apache.ibatis.jdbc.SQL;
 
public class CategoryDynaSqlProvider {
    public String list() {
         return new SQL()
                 .SELECT("*")
                 .FROM("category_")
                 .toString();
         
    }
    public String get() {
        return new SQL()
                .SELECT("*")
                .FROM("category_")
                .WHERE("id=#{id}")
                .toString();
    }
     
    public String add(){
        return new SQL()
                .INSERT_INTO("category_")
                .VALUES("name", "#{name}")
                .toString();
    }
    public String update(){
        return new SQL()
                .UPDATE("category_")
                .SET("name=#{name}")
                .WHERE("id=#{id}")
                .toString();
    }
    public String delete(){
        return new SQL()
                .DELETE_FROM("category_")
                .WHERE("id=#{id}")
                .toString();
    }
     
}
```

---
<h2 id="2">修改CategoryMapper</h2><br>

```java
package com.how2java.mapper;
  
import java.util.List;
 
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
 
import com.how2java.CategoryDynaSqlProvider;
import com.how2java.pojo.Category;
  
public interface CategoryMapper {
  
  	// @Insert(" insert into category_ ( name ) values (#{name}) ")  
    @InsertProvider(type=CategoryDynaSqlProvider.class,method="add") 
    public int add(Category category); 
        
    @DeleteProvider(type=CategoryDynaSqlProvider.class,method="delete")
    public void delete(int id); 
        
    @SelectProvider(type=CategoryDynaSqlProvider.class,method="get") 
    public Category get(int id); 
      
    @UpdateProvider(type=CategoryDynaSqlProvider.class,method="update") 
    public int update(Category category);  
        
    @SelectProvider(type=CategoryDynaSqlProvider.class,method="list")     
    public List<Category> list(); 
}
```

---
<h2 id="3">测试参考</h2><br>[注解-CRUD](./注解CRUD.md)