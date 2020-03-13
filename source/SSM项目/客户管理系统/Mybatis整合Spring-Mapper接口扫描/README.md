把[去掉Mapper实现类](../Mybatis整合Spring-去掉Mapper实现类)的项目升级成使用Mapper接口扫描的方式（推荐）
===

修改applicationContext.xml

去掉原本的Mapper接口配置

添加如下代码：

```xml
	<!-- Mapper接口的扫描 -->
	<!-- 
		如果采用Mapper接口包扫描的方式，那么每个Mapper接口在Spring容器中的id名称，是它的类名:
		例如 CustomerMapper  ->  customerMapper
	 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 配置Mapper接口所在的包路径 -->
		<property name="basePackage" value="cn.sm1234.dao"></property>
	</bean>
```

在测试类中获取对象的时候，就通过 `customerMapper`这个id来获取

```java
// 2.获取对象
		CustomerMapper customerMapper = (CustomerMapper)ac.getBean("customerMapper");
```

