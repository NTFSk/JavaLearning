package com.ischoolbar.programmer.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.Student;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.service.StudentService;
import com.ischoolbar.programmer.service.UserService;
import com.ischoolbar.programmer.util.CpachaUtil;
import com.mysql.jdbc.StringUtils;



/*
 * 系统主页控制器
 */

@RequestMapping("/system")
@Controller
public class SystemController {

	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "system/index";
	}
	
	// ModelAndView方式
	/**
	 * 登陆页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
	}
	
	/**
	 * 注销登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_out", method = RequestMethod.GET)
	public String login_out(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return "redirect:login";
	}
	
	/**
	 * 登录表单提交
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody // 按照json格式返回值
	public Map<String, String> login(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="vcode", required=true) String vcode,
			@RequestParam(value="type", required=true) Integer type,
			HttpServletRequest request
			) {
		Map<String, String> ret = new HashMap<String, String>();
		// 判断用户名是否为空
		if(StringUtils.isNullOrEmpty(username)) {
			ret.put("type","error");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		// 判断密码是否为空
		if(StringUtils.isNullOrEmpty(password)) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空");
			return ret;
		}
		// 判断验证码是否为空
		if(StringUtils.isNullOrEmpty(vcode)) {
			ret.put("type", "error");
			ret.put("msg", "验证码不能为空");
			return ret;
		}
		/**
		 * 在方法getCpacha中我们向session中加入一个属性loginCpacha
		 * 属性值是String类的验证码
		 */
		String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
		//  如果从Session中提取的验证码字符串为空，说明session中的属性失效
		if(StringUtils.isNullOrEmpty(loginCpacha)) {
			ret.put("type", "error");
			ret.put("msg", "长时间未操作，会话已失效，请刷新后重试！");
			return ret;
		}
		// 无视用户输入的大小写
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
			ret.put("type", "error");
			ret.put("msg", "验证码输入错误！");
			return ret;
		}
		// 如果以上操作都顺利进行，那么清除session中的验证码属性
		request.getSession().setAttribute("loginCpacha", null);
		//管理员登录
		if(type == 1) {
			// 从数据库查找用户信息
			User user = userService.findByUserName(username);
			if(user == null) {
				ret.put("type", "error");
				ret.put("msg", "不存在该用户！");
				return ret;
			}
			if(!password.equals(user.getPassword())) {
				ret.put("type", "error");
				ret.put("msg", "密码错误！");
				return ret;
			}
			/**
			 * 把管理员user对象，放入到session的user属性中
			 * 在spring-mvc的配置文件中
			 * 后端拦截器会对session中的user属性进行判断
			 */
			request.getSession().setAttribute("user", user);
		}
		// 学生登陆
		if(type == 2){
			Student student = studentService.findByUserName(username);
			if(student == null) {
				ret.put("type", "error");
				ret.put("msg", "不存在该学生！");
				return ret;
			}
			if(!password.equals(student.getPassword())) {
				ret.put("type", "error");
				ret.put("msg", "密码错误！");
				return ret;
			}
			request.getSession().setAttribute("user", student);
			
		}
		request.getSession().setAttribute("userType", type);
		
		ret.put("type", "success");
		ret.put("msg", "登陆成功！");
		return ret;
	}
	
	/**
	   *  显示 验证码
	 * @param request
	 * @param vl
	 * @param w
	 * @param h
	 * @param response
	 */
	@RequestMapping(value="/get_cpacha", method = RequestMethod.GET)
	public void getCpacha(HttpServletRequest request, 
			@RequestParam(value="vl", defaultValue="4", required=false) Integer vl,
			@RequestParam(value="w", defaultValue="98", required=false) Integer w,
			@RequestParam(value="h", defaultValue="33", required=false) Integer h,
			HttpServletResponse response) {
		// 创建验证码对象
		CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
		// 生成验证码
		String generatorVCode = cpachaUtil.generatorVCode();
		// 在session中包含验证码的属性
		request.getSession().setAttribute("loginCpacha", generatorVCode);
		// 给验证码加上干扰线
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
