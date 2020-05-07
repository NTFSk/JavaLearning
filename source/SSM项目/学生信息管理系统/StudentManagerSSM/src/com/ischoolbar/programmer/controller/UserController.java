package com.ischoolbar.programmer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.service.UserService;
import com.mysql.jdbc.StringUtils;

/**
 * �û�������Ա��������
 * @author Crspin
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	public UserService userService;
	/**
	 *  �û������б�ҳ
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/user_list");
		return model;
	}
	
	/**
	 * ��ȡ�û��б�
	 * @param username
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value = "username",required = false,defaultValue = "") String username,
			Page page
			){
		/**
		 * ret�������غ�̨���ݸ�ǰ��
		 * queryMap������Ż�ȡ�����û�����
		 */
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", "%"+username+"%");
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		/**
		 * datagrid��ҳ����Ҫ���̨����rows��total����
		 */
		ret.put("rows", userService.findList(queryMap));
		ret.put("total", userService.getTotal(queryMap));
		return ret;
	}
	
	
	/**
	 * ����û�����
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		// ���ڴ�ŷ���ֵ������
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type","error");
			ret.put("msg", "���ݰ󶨳�������ϵ����Ա��");
			return ret;
		}
		// �����û����Ƿ�Ϊ��
		if(StringUtils.isNullOrEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "�û�������Ϊ�գ�");
			return ret;
		}
		// ���������Ƿ�Ϊ��
		if(StringUtils.isNullOrEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "���벻��Ϊ�գ�");
			return ret;
		}
		User exitUser = userService.findByUserName(user.getUsername());
		if(exitUser != null) {
			ret.put("type","error");
			ret.put("msg", "�û����Ѿ����ڣ�");
			return ret;
		}
		if(userService.add(user) <= 0) {
			ret.put("type","error");
			ret.put("msg", "���ʧ�ܣ�");
			return ret;
		}
		ret.put("type","success");
		ret.put("msg", "��ӳɹ���");
		return ret;
	}
	
	/**
	 * �༭�û�����
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
		// ���ڴ�ŷ���ֵ������
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type","error");
			ret.put("msg", "���ݰ󶨳�������ϵ����Ա��");
			return ret;
		}
		// �����û����Ƿ�Ϊ��
		if(StringUtils.isNullOrEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "�û�������Ϊ�գ�");
			return ret;
		}
		// ���������Ƿ�Ϊ��
		if(StringUtils.isNullOrEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "���벻��Ϊ�գ�");
			return ret;
		}
		// ����ѡ�е��û����û�������ȡUser����
		User exitUser = userService.findByUserName(user.getUsername());
		/**
		 * ������û���Ϊ���޸����룬��ôexitUser��Ȼ����
		 * ��ʱ��Ҫȥ���exitUser��id�ͱ�ѡ�е���һ���û����ݵ�id�Ƿ���ͬ�������ͬ��������ͬһ���û������Խ����޸�
		 * �����ͬ����˵�����ݿ����Ѿ����ڱ���û�ռ��������û���
		 * ��ô��Ҫ��ʾ���û����Ѵ��ڡ�
		 */
		if(exitUser != null) {
			if(user.getId() != exitUser.getId()) {
				ret.put("type","error");
				ret.put("msg", "�û����Ѿ����ڣ�");
				return ret;
			}
			
		}
		if(userService.edit(user) <= 0) {
			ret.put("type","error");
			ret.put("msg", "���ʧ�ܣ�");
			return ret;
		}
		ret.put("type","success");
		ret.put("msg", "��ӳɹ���");
		return ret;
	}
	
	/**
	 * ����ɾ������
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestParam(value="ids[]", required = true) Long[] ids
			){
		// ���ڴ�ŷ���ֵ������
		Map<String, String> ret = new HashMap<String, String>();
		// �ж�ɾ����ť�����ʱ��û�����ݱ�ѡ��
		if(ids == null) {
			ret.put("type","error");
			ret.put("msg", "��ѡ��Ҫɾ�������ݣ�");
			return ret;
		}
		/**
		 * �ѱ�ѡ�е��û���id��� "(1��2��3)"����ʽ
		 */
		String idsString = "";
		for(Long id:ids) {
			idsString += id + ",";
		}
		idsString = idsString.substring(0,idsString.length()-1);
		if(userService.delete(idsString)<=0) {
			ret.put("type","error");
			ret.put("msg", "ɾ��ʧ�ܣ�");
			return ret;
		}
		ret.put("type","success");
		ret.put("msg", "ɾ���ɹ���");
		return ret;
	}
}
