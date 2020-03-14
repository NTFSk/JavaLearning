1. 导入jar包
   ---

   

2. 配置web.xml
   ---

   1)  启动Spring， 加载 applicationContext.xml

   - 配置监听器启动Spring

     ```xml
     <!-- 启动Spring -->
     	<listener>
     		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
     	</listener>
     	<!-- listener默认加载的是WEB-INF目录下的applicationContext.xml文件 -->
     	<!-- 所以需要修改一下路径 -->
     	<context-param>
     		<param-name>contextConfigLocation</param-name>
     		<param-value>classpth:applicationContext.xml</param-value>
     	</context-param>
     ```

     

   2） 启动SpringMVC, 加载 spring-mvc.xml

   ```xml
   <!-- 启动SpringMVC -->
   	<!-- 通过SpringMVC提供的启动类来启动 -->
   	<servlet>
   		<servlet-name>DispatcherServlet</servlet-name>
   		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
   		<!-- 参数: 读取spring-mvc.xml -->
   		<init-param>
   			<param-name>contextConfigLocation</param-name>
   			<param-value>classpath:spring-mvc.xml</param-value>
   		</init-param>
   	</servlet>
   	
   	<servlet-mapping>
   		<servlet-name>DispatcherServlet</servlet-name>
   		<url-pattern>*.action</url-pattern>
   	</servlet-mapping>
   ```

   

3. 配置spring-mvc.xml

4. 编写Controller

5. 编写页面



