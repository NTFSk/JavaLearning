

---

[1.控制类](#1)<br>
[2.视图](#2)<br>

<h3 id="1">控制类</h3><br>

为check()方法提供`HttpSession session`参数，就可以在方法体中使用session了

```java
package controller;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class IndexController {
 	
 	// 映射方法到/check路径
    @RequestMapping("/check")
    public ModelAndView check(HttpSession session) {
    	// 获取count属性的值
        Integer i = (Integer) session.getAttribute("count");
        if (i == null)
            i = 0;
        i++;
        // 修改count属性的值
        session.setAttribute("count", i);
        // check.jsp
        ModelAndView mav = new ModelAndView("check");
        return mav;
    }
 
}

```

<h3 id="2">check.jsp</h3>

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
session中记录的访问次数：${count}
```