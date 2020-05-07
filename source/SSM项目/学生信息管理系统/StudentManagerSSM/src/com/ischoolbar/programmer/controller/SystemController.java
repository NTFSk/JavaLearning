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
 * ϵͳ��ҳ������
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
	
	// ModelAndView��ʽ
	/**
	 * ��½ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
	}
	
	/**
	 * ע����¼
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_out", method = RequestMethod.GET)
	public String login_out(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return "redirect:login";
	}
	
	/**
	 * ��¼���ύ
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody // ����json��ʽ����ֵ
	public Map<String, String> login(
			@RequestParam(value="username", required=true) String username,
			@RequestParam(value="password", required=true) String password,
			@RequestParam(value="vcode", required=true) String vcode,
			@RequestParam(value="type", required=true) Integer type,
			HttpServletRequest request
			) {
		Map<String, String> ret = new HashMap<String, String>();
		// �ж��û����Ƿ�Ϊ��
		if(StringUtils.isNullOrEmpty(username)) {
			ret.put("type","error");
			ret.put("msg", "�û�������Ϊ��");
			return ret;
		}
		// �ж������Ƿ�Ϊ��
		if(StringUtils.isNullOrEmpty(password)) {
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ��");
			return ret;
		}
		// �ж���֤���Ƿ�Ϊ��
		if(StringUtils.isNullOrEmpty(vcode)) {
			ret.put("type", "error");
			ret.put("msg", "��֤�벻��Ϊ��");
			return ret;
		}
		/**
		 * �ڷ���getCpacha��������session�м���һ������loginCpacha
		 * ����ֵ��String�����֤��
		 */
		String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
		//  �����Session����ȡ����֤���ַ���Ϊ�գ�˵��session�е�����ʧЧ
		if(StringUtils.isNullOrEmpty(loginCpacha)) {
			ret.put("type", "error");
			ret.put("msg", "��ʱ��δ�������Ự��ʧЧ����ˢ�º����ԣ�");
			return ret;
		}
		// �����û�����Ĵ�Сд
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
			ret.put("type", "error");
			ret.put("msg", "��֤���������");
			return ret;
		}
		// ������ϲ�����˳�����У���ô���session�е���֤������
		request.getSession().setAttribute("loginCpacha", null);
		//����Ա��¼
		if(type == 1) {
			// �����ݿ�����û���Ϣ
			User user = userService.findByUserName(username);
			if(user == null) {
				ret.put("type", "error");
				ret.put("msg", "�����ڸ��û���");
				return ret;
			}
			if(!password.equals(user.getPassword())) {
				ret.put("type", "error");
				ret.put("msg", "�������");
				return ret;
			}
			/**
			 * �ѹ���Աuser���󣬷��뵽session��user������
			 * ��spring-mvc�������ļ���
			 * ������������session�е�user���Խ����ж�
			 */
			request.getSession().setAttribute("user", user);
		}
		// ѧ����½
		if(type == 2){
			Student student = studentService.findByUserName(username);
			if(student == null) {
				ret.put("type", "error");
				ret.put("msg", "�����ڸ�ѧ����");
				return ret;
			}
			if(!password.equals(student.getPassword())) {
				ret.put("type", "error");
				ret.put("msg", "�������");
				return ret;
			}
			request.getSession().setAttribute("user", student);
			
		}
		request.getSession().setAttribute("userType", type);
		
		ret.put("type", "success");
		ret.put("msg", "��½�ɹ���");
		return ret;
	}
	
	/**
	   *  ��ʾ ��֤��
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
		// ������֤�����
		CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
		// ������֤��
		String generatorVCode = cpachaUtil.generatorVCode();
		// ��session�а�����֤�������
		request.getSession().setAttribute("loginCpacha", generatorVCode);
		// ����֤����ϸ�����
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
