web.xml加载过程（步骤）
---

​	当启动一个WEB项目的时候，容器（JBoss、Tomcat等）会先读取项目web.xml文件里的配置，当这一步骤没有出错并且完成之后，项目才能正确地被启动起来。

1. 启动WEB项目的时候，容器首先会去web.xml读取两个节点：`<listener> </listener>` 和 `<context-param> </context-param>`。
2. 紧接着，容器创建一个`ServletContext`（servlet上下文）, 这个WEB项目内部的所有servlet都共享这个对象。
3. 容器以`<context-param> </context-param>`的 name 作为键， value 作为值，将其转化为键值对，存入`SerlectContext`（servlet上下文）。
4. 容器创建`<listener> </listener>` 中的类实例， 根据配置的class类路径`<listener-class>`来创建监听，
5. 接着，容器会读取`<filter> </filter>`，根据指定的类路径来实例化过滤器。
6. 以上都是在WEB项目还没有完全启动起来的时候就已经完成了的工作。如果系统中有Servlet，则Servlet式再第一次发起请求的时候被实例化的，而且一般不会被容器销毁，它可以服务于多个用户的请求。所以Servlet的初始化比上面提到的几点要迟。
7.  总的来说，web.xml的加载顺序是:`<context-param>`—>`<listener>`—>`<filter>`—>`<servlet>`。其中，如果web.xml中出现了相同的元素，则按照在配置文件中出现的先后顺序来加载。
8. web.xml中可以定义多个`<filter>`，与`<filter>`相关的一个元素是`<filter-mapping>`，注意，对于拥有相同`<filter-name>`的 `<filter>` 和 `<filter-mapping>` 元素而言，`<filter-mapping>`必须出现在`<filter>`之后，否则当解析到`<filter-mapping>`时，它所对应的`<filter-name>`还未定义。web容器启动初始化每个`<filter>`时，按照`<filter>`出现的顺序来初始化的，当请求资源匹配多个`<filter-mapping>`时，`<filter>`拦截资源是按照`<filter-mapping>`元素出现的顺序来依次调用doFilter()方法的。`<servlet>`同`<filter>`类似，此处不再赘述。

