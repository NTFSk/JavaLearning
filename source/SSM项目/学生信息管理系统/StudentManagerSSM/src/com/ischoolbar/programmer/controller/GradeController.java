package com.ischoolbar.programmer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.Grade;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.service.GradeService;

/**
 * �꼶��Ϣ����
 * @author Crspin
 *
 */
@RequestMapping("/grade")
@Controller
public class GradeController {

	@Autowired
	private GradeService gradeService;
	
	/**
	 * �꼶�б�ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("grade/grade_list");
		return model;
	}
	
	/**
	 * ��ȡ�꼶�б�
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value = "name",required = false,defaultValue = "") String name,
			Page page
			){
		/**
		 * ret�������غ�̨���ݸ�ǰ��
		 * queryMap������Ż�ȡ�����û�����
		 */
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "%"+name+"%");
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		/**
		 * datagrid��ҳ����Ҫ���̨����rows��total����
		 */
		ret.put("rows", gradeService.findList(queryMap));
		ret.put("total", gradeService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * ����꼶��Ϣ
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Grade grade){
		Map<String, String> ret = new HashMap<String, String>();
		// �ж����ύ���꼶�����Ƿ�Ϊ��
		if(StringUtil.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "�꼶���Ʋ���Ϊ�գ�");
			return ret;
		}
		if(gradeService.add(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "�꼶���ʧ�ܣ�");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�꼶��ӳɹ���");
		return ret;
	}
	
	/**
	 * �༭�꼶��Ϣ
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Grade grade){
		Map<String, String> ret = new HashMap<String, String>();
		// �ж����ύ���꼶�����Ƿ�Ϊ��
		if(StringUtil.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "�꼶���Ʋ���Ϊ�գ�");
			return ret;
		}
		if(gradeService.edit(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "�꼶�޸�ʧ�ܣ�");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�꼶�޸ĳɹ���");
		return ret;
	}
	
	/**
	 * ɾ���꼶��Ϣ
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestParam(value = "ids[]",required = true) Long[] ids
			){
		Map<String, String> ret = new HashMap<String, String>();
		if(ids == null || ids.length == 0) {
			ret.put("type", "error");
			ret.put("msg", "������ѡ��һ�����ݽ���ɾ����");
			return ret;
		}
		
		try {
			if(gradeService.delete(com.ischoolbar.programmer.util.StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
				ret.put("type", "error");
				ret.put("msg", "ɾ��ʧ�ܣ�");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "���꼶�´��ڰ༶��Ϣ���޷�ɾ����");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "�꼶ɾ���ɹ���");
		return ret;
		
	}
	
}
