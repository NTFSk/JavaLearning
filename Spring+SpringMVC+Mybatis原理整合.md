Spring的核心有两部分：
---



* __IoC：控制反转 __
  举例来说，在之前的操作中，比方说有一个类，我们想要调用类里面的方法(不是静态方法)，就要创建类的对象，使用对象调用方法实现。对于Spring来说，Spring创建对象的过程，不是在代码里面实现的，而是交给Spring来进行配置实现的。
* __AOP：面向切面编程 __
  主要是管理系统层的业务，比如日志，权限，事物等。AOP是将封装好的对象剖开，找出其中对多个对象产生影响的公共行为，并将其封装为一个可重用的模块，这个模块被命名为切面（aspect），切面将那些与业务逻辑无关，却被业务模块共同调用的逻辑提取并封装起来，减少了系统中的重复代码，降低了模块间的耦合度，同时提高了系统的可维护性。

为什么使用Spring
---

* 方便解耦，简化开发。 
  Spring就是一个大工厂，可以将所有对象的创建和依赖关系的维护，交给Spring管理。
* AOP编程的支持 
  Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能。
* 声明式事务的支持 
  只需要通过配置就可以完成对事务的管理，而无须手动编程。
* 方便程序的测试 
  Spring对Junit4支持，可以通过注解方便的测试Spring程序。
* 方便集成各种优秀的框架 
  Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架(如：Struts2、Hibernate、MyBatis、Quartz等)的直接支持。
* 降低JavaEE API的使用难度 
  Spring对JavaEE开发中非常难用的一些API(JDBC、JavaMail、远程调用等)，都提供了封装，使这些API应用难度大大降低。



常用的Spring注释：
---

* @Service用于标注业务层组件、 
* @Controller用于标注控制层组件（如struts中的action）
* @Repository用于标注数据访问组件，即DAO组件。
* @Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
* @Autowired 默认按类型装配，如果我们想使用按名称装配，可以结合@Qualifier注解一起使用。如下：
* @Autowired @Qualifier("personDaoBean") 存在多个实例配合使用
* @Resource默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
  

[Spring常用的三种注入方式](https://blog.csdn.net/ShyTan/article/details/82142285)

---



SpringMVC实现MVC框架的具体方法如下图所示：
---



    a、前端控制器DispatchServlet接受客户端浏览器发送的请求 
    b、前端控制器DispatchServlet调用处理器映射器HandlerMapping查找相应的处理器 
    c、处理器映射器根据URL查找处理器Handler，并给前端控制器返回生成的处理器和相应的处理器拦截器HandlerIntercepter 
    d、前端控制器调用处理器适配器HandlerAdapter 
    e、处理器适配器调用相应的处理器 
    f、处理器给处理器适配器返回ModelAndView(包含逻辑视图名) 
    g、处理器将ModelAndView返回给前端控制器 
    h、前端控制器将ModelAndView传给视图解析器ViewResolver 
    i、视图解析器ViewResolver解析后将具体的视图View返回给前端控制器 
    j、前端控制器对视图进行渲染（视图渲染：将模型数据（在ModelAndView中）填充到request域中） 
    k、前端控制器将视图返回给客户端浏览器

Spring MVC的优势
---

1，清晰的角色划分：前端控制器（DispatcherSevlet），请求到处理器映射（HandlerMapping），处理器适配器（HandlerAdapter），视图解析器（ViewResolver），处理器或页面控制器（Controller），验证器（Validator），命令对象（Command请求参数绑定到的对象就叫命令对象），表单对象（Form Object提供给表单展示和提交到的对象就叫表单对象）。

2，分工明确，而且扩展点相当灵活，可以很容易扩展，虽然几乎不需要

3，由于命令对象就是一个POJO，无需继承框架特定API，可以使用命令对象直接作为业务对象

4，和Spring其他框架无缝结合，是其他web框架所不具备的

5，可适配，通过HandlerAdapter可以支持任意的类作为处理器

6，可定制行，HandlerMapping，ViewResolver等能够非常简单的定制

7，功能强大的数据验证，格式化，绑定机制；

8，利用Spring提供的Mock对象能够非常简单的进行Web层单元测试；

9，本地化，主题的解析的支持，使我们更容易进行国际化和主题的切换；

10，强大的JSP标签库，使Jsp编写更容易。

SpringMVC常用注解：
---

`@Controller`

　　负责注册一个bean 到spring 上下文中

`@RequestMapping`

　　注解为控制器指定可以处理哪些 URL 请求

`@RequestBody`

　　该注解用于读取Request请求的body部分数据，使用系统默认配置的HttpMessageConverter进行解析，然后把相应的数据绑定到要返回的对象上 ,再把HttpMessageConverter返回的对象数据绑定到 controller中方法的参数上

`@ResponseBody`

　　 该注解用于将Controller的方法返回的对象，通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区



SpringMVC常用知识：https://blog.csdn.net/ShyTan/article/details/82142393
---



<h3>MyBatis</h3>



​	MyBatis 是一款优秀的持久层框架，它可以完美的定制化数据库、存放储蓄过程和其他高级的映射。MyBatis 真的很完美的`避免了几乎所有的JDBC的相关的编码以及手动的设置数据参数并得到最终结果的集。`

​	MyBatis应用程序根据XML配置文件创建SqlSessionFactory，SqlSessionFactory在根据配置，配置来源于两个地方，一处是配置文件，一处是Java代码的注解，获取一个SqlSession。SqlSession包含了执行sql所需要的所有方法，可以通过SqlSession实例直接运行映射的sql语句，完成对数据的增删改查和事务提交等，用完之后关闭SqlSession。

__优点：__

1. 基于SQL语法，简单易学。

2. 能了解底层组装过程。

3. SQL语句封装在配置文件中，便于统一管理与维护，降低了程序的耦合度。

4. 程序调试方便

5. 与传统JDBC比较较少了大量的代码量，是最简单的持久化框架

6. sql代码从程序代码中彻底分离，可重用，增强了项目中的分工，增强了移植性

所有sql语句，全部定义在xml（建议）中。也可以通过注解的方式在接口上实现。这些映射文件称之为mapper

__缺点：__

1. sql工作量很大，尤其是字段多、关联表多时，更是如此。
2. sql依赖于数据库，导致数据库移植性差。
3. 框架还是比较简陋，功能尚有缺失，虽然简化了数据绑定代码，但是整个底层数据库查询实际还是要自己写的，工作量也比较大，而且不太容易适应快速数据库修改。 
4. 二级缓存机制不佳

__Mybatis详解：https://blog.csdn.net/ShyTan/article/details/81905135__<br>
__常用配置：https://blog.csdn.net/ShyTan/article/details/82142520__<br>
————————————————
版权声明：本文为CSDN博主「打好基础多看书」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/ShyTan/article/details/81904829