单独使用mybatis框架的一些注意事项
===

[1.导入包]()

[2.建立数据库和表]()

[3.建立实体类]()

[4.建立Mapper接口]()

[5.建立sql映射文件（xml文件）]()

[6.建立核心Mybatis核心配置文件]()

[7.编写测试类]()



<h3 id="1">1. 要导入的包</h3>

[点此连接](./要导入的包)

<h3 id="2">2. 建立数据库和表</h3>

```sql
CREATE TABLE `t_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



<h3 id="3">3. 建立实体类</h3>

```java
package cn.sm1234.domain;

public class Customer {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private String name;
	private String gender;
	private String telephone;
	private String address;
}

```

<h3 id="4">4. 建立mapper接口</h3>

```
package cn.sm1234.dao;

import cn.sm1234.domain.Customer;

public interface CustomerMapper {
	/**
	 * 添加一个客户
	 */
	public void saveCustomer(Customer customer);
}

```

<h3 id="5">5. 建立sql映射文件</h3>

__给mapper接口中的方法，配置上具体的sql语句__

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 该文件编写mybatis中的mapper接口里面的方法提供对应的sql语句 -->
<mapper namespace="cn.sm1234.dao.CustomerMapper">
	
	<!-- 添加客户 -->
	<insert id="saveCustomer" parameterType="cn.sm1234.domain.Customer">
		INSERT INTO ssm.t_customer 
			( 
			NAME, 
			gender, 
			telephone, 
			address
			)
			VALUES
			( 
			#{name}, 
			#{gender}, 
			#{telephone}, 
			#{address}
			)
	</insert>
	

</mapper>
```

<h3 id="6">6. mybatis的核心配置文件</h3>

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">

<!-- 6.核心配置 -->
<configuration>
	
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<!-- 提供连接数据库用的驱动，数据库名称，编码方式，账号密码 -->
				<property name="driver" value="com.mysql.jdbc.Driver"/>
				<property name="url" value="jdbc:mysql://localhost:3306/ssm?characterEncoding=UTF-8"/>
				<property name="username" value="root"/>
				<property name="password" value="123123"/>
			</dataSource>
		</environment>
	</environments>
	
	<!-- 查找sql映射文件 -->
	<mappers>
		<mapper resource="mapper/CustomerMapper.xml"/> 
        
	</mappers>
</configuration>
```



<h3 id="7">7.  测试类</h3>

```java
package cn.sm1234.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

public class MyBatisTest {
	@Test
	public void test() throws IOException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
		// 创建sqlSessionFactory
		SqlSessionFactory factory = builder.build(is);
		SqlSession sqlSession = factory.openSession();
		
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		
		Customer customer = new Customer();
		customer.setName("张三");
		customer.setGender("男");
		customer.setTelephone("123123");
		customer.setAddress("福建厦门");
		
		mapper.saveCustomer(customer);
		
		// 提交事务
		sqlSession.commit();
		// 关闭资源
		sqlSession.close();
	}
}

```

