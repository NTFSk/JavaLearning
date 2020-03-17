一、选题的目的和现实意义
	实习是毕业前的重要任务，若能在实习中学习到自己所需要的知识，则对于毕业和以后的工作来说都是十分有帮助的。我希望开发出实用且经济实惠的中小型系统，因为中小型系统更加利于开发和维护。因此我选择了开发客户信息管理系统。每个企业都有其相应的客户群体,如何方便快捷地对客户的信息进行管理是每个企业都要面对的问题。本系统使用SSM框架+jQuery EasyUI技术并搭载tomcat服务器和主流MySQL数据库，在方便开发的同时，能开发出美观且方便使用的界面，并且很好地保护了客户信息地安全。提升了系统地易用性和安全性，并大大降低了后期维护的成本。

二、系统的初步设计

​	系统包括一个主窗体和4个子窗体，主要的功能包括：客户信息分页显示、客户信息添加、客户信息修改、客户信息删除等等。在主窗体的左侧有一个侧拉菜单，侧拉菜单中包含一个下拉菜单，点击之后在主窗体的右侧显示出所有客户的信息。客户的信息主要显示在DataGrid表格中，搭配上EasyUI的分页组件和工具条，能十分便捷的对客户的信息进行管理和调整。客户信息的新增和修改功能使用相同的编辑框和控制类方法，并在后台进行逻辑区分，方便了程序的维护和测试。

三、应用技术简介

1.Spring框架

​	Spring 是最受欢迎的企业级 Java 应用程序开发框架，数以百万的来自世界各地的开发人员使用 Spring 框架来创建性能好、易于测试、可重用的代码。Spring 框架是一个开源的 Java 平台，它最初是由 Rod Johnson 编写的，并且于 2003 年 6 月首次在 Apache 2.0 许可下发布。
​	Spring 是轻量级的框架，其基础版本只有 2 MB 左右的大小。Spring 框架的核心特性是可以用于开发任何 Java 应用程序，但是在 Java EE 平台上构建 web 应用程序是需要扩展的。 Spring 框架的目标是使 J2EE 开发变得更容易使用，通过启用基于 POJO 编程模型来促进良好的编程实践。

2.SpringMVC框架

​	SpringMVC全称是Spring Web MVC，是spring框架一部分，是一个mvc的框架，和struts2一样是一个表现层框架。Spring MVC 实现了即用的 MVC 的核心概念。它为控制器和处理程序提供了大量与此模式相关的功能。并且当向 MVC 添加反转控制（Inversion of Control，IoC）时，它使应用程序高度解耦，提供了通过简单的配置更改即可动态更改组件的灵活性。Spring MVC 为您提供了完全控制应用程序的各个方面的力量。
​	Spring 的 Web MVC 模块是围绕 DispatcherServlet 而设计的。DispatcherServlet 给处理程序分派请求，执行视图解析，并且处理语言环境和主题解析，此外还为上传文件提供支持。

3.Myabtis

​	Mybatis的前身是Apache的开源框架iBatis，与Hibernate一样是一个Java持久层的框架。
​	Mybatis的优势在于灵活，它几乎可以替代JDBC，同时提供了接口编程。目前Mybatis的数据访问层DAO（Data Access Object）是不需要实习类的，它只需要一个接口和XML。Mybatis提供自动映射，动态SQL，级联，缓存，注解，代码和SQL分离等特性，使用方便，同时也对SQL进行优化。因为其具有封装少，映射多样化，支持存储过程，可以进行SQL优化等特点，是的它取代了Hibernate成为Java互联网中首选持久层框架。

4.Tomcat

​	Tomcat 服务器是一个免费的开放源代码的Web 应用服务器，属于轻量级应用服务器，在中小型系统和并发访问用户不是很多的场合下被普遍使用，是开发和调试JSP 程序的首选。对于一个初学者来说，可以这样认为，当在一台机器上配置好Apache 服务器，可利用它响应HTML页面的访问请求。实际上Tomcat是Apache 服务器的扩展，但运行时它是独立运行的，所以当你运行tomcat 时，它实际上作为一个与Apache 独立的进程单独运行的。

5、Spring-Mybatis整合

spring和mybatis的整合是通过applicationContext.xml文件实现的，主要是完成 dao层和pojo的自动扫描装配。

	<!-- 创建DataSource -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
		<property name="url" value="${jdbc.url}"/>
		<property name="driverClassName" value="${jdbc.driverClass}"/>
		<property name="username" value="${jdbc.user}"/>
		<property name="password" value="${jdbc.password}"/>
		<property name="maxActive" value="10"/>
		<property name="maxIdle" value="5"/>
	</bean>	
	
	<!-- Mapper接口的扫描 -->
	<!-- 
	注意：如果使用Mapper接口包扫描，那么每个Mapper接口在Spring容器中的id名称为类名： 例如 CustomerMapper -> customerMapper
	-->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
	<!-- 配置Mapper接口所在包路径  -->
	<property name="basePackage" value="cn.fsh.dao"/>
	</bean>
	
	<!-- 开启Spring的IOC注解扫描 -->
	<context:component-scan base-package="cn.fsh"/>



四、客户功能开发示例

​	从客户数据显示这一功能，展示开发的流程和具体实现。

​	首先，是对前端页面的开发，定义所需要的组件和方法。然后在控制类中添加上对应的方法

	@RequestMapping("/list")
	@ResponseBody  // 用于转换对象为json
	public List<Customer> list(){
		// 查询数据
		List<Customer> list = customerService.findAll();
		
		return list;
	}
接着是业务接口和业务实现类的设计

```
public List<Customer> findAll();
```

	public List<Customer> findAll() {
		return customerMapper.findAl();
	}
最后是Mapper和Mapper映射文件的设计

```
public List<Customer> findAl();
```

	<select id="findAl" resultType="cn.fsh.domain.Customer">
		SELECT id,
			NAME,
			gender,
			telephone,
			address
			FROM
		ssm.t_customer
	</select>
五、计划实现情况

​	按照之前的计划，大部分的功能已经实现。系统运行后，通过主窗体左侧的侧拉栏点击客户管理按钮，在主窗体的右侧调用显示客户信息。通过Easy UI提供的按钮配合控制类中的方法，可以轻松的实现客户信息的管理。对数据库的操作由Mybatis框架进行控制，同时添加了Spring事务功能，保证数据的正常插入和删除。

六、遇到的困难和解决方案

​	在添加新数据和修改原数据时，都要用到同一个客户信息的编辑窗口，那么程序上要如何去辨别，用户按下保存按钮的时候，执行的是添加操作，还是保存操作呢？

​	解决方案：在表单中添加一个隐藏的id项，把id属性提交到后台，通过id来判别执行的是修改操作还是新增操作——id是数据提交后自动生成的，所以如果是新增操作，那么是查询不到id的；如果是修改操作，则可以查询到id。

前端

```java
 	<div id="win" class="easyui-window" title="客户信息编辑" style="width:400px;height:300px"
  		data-options="iconCls:'icon-save',modal:true, closed:true">
  		<form id="editForm" method="post">
  			<%-- 提供id隐藏域，用来区分新增和修改--%>
  			<input type="hidden" name="id"/>
  			客户姓名：<input type="text" name="name" class="easyui-validatebox" data-options="required:true"/><br/>
		   	客户性别：
		   	<input type="radio" name="gender" value="男"/>男
		   	<input type="radio" name="gender" value="女"/>女
		   	<br/>
		   	客户手机：<input type="text" name="telephone"/><br/>
		   	客户地址：<input type="text" name="address"/><br/>
		   	<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
  		</form>
  	</div>
```



业务类

		public void save(Customer customer) {
			// TODO Auto-generated method stub
			// 如果用户id不为空，则是修改
			// 为空则是新增
			if(customer.getId()!=null) {
				customerMapper.update(customer);
			}else {
				customerMapper.save(customer);
			}
		}
七、总结

​	经过六周的学习，提高了SSM框架的使用熟练度，增加了相关方面的知识，巩固和更全面的掌握了MySQL数据库的使用，以及前后端的交互；期间遇到了一些问题，然后通过网络搜索相关的文献和案例，最终都找到了解决的方法，圆满地完成了实习任务。