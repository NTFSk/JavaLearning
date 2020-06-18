#### 在pom.xml中增加对JSP的支持

```xml
  
<!-- servlet依赖. -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
</dependency> 

<!-- tomcat的支持.-->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
</dependency>    

```

#### application.properties

在src/main/resources 目录下增加 application.properties文件，用于视图重定向jsp文件的位置

```
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

#### HelloController

```java

import java.text.DateFormat;
import java.util.Date;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
  
@Controller
public class HelloController {
  
    @RequestMapping("/hello")
    public String hello(Model m) {
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "hello";
    }
}
```

#### hello.jsp

在main目录下，新建-> webapp/WEB-INF/jsp 目录。

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
Hi JSP. 现在时间是  ${now}
```

![启动测试](https://stepimagewm.how2j.cn/7153.png)