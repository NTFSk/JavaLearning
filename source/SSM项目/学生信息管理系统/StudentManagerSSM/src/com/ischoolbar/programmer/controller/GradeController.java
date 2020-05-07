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
 * 年级信息管理
 * @author Crspin
 *
 */
@RequestMapping("/grade")
@Controller
public class GradeController {

	@Autowired
	private GradeService gradeService;
	
	/**
	 * 年级列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("grade/grade_list");
		return model;
	}
	
	/**
	 * 获取年级列表
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
		 * ret用来返回后台数据给前端
		 * queryMap用来存放获取到的用户数据
		 */
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "%"+name+"%");
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		/**
		 * datagrid分页操作要求后台返回rows，total参数
		 */
		ret.put("rows", gradeService.findList(queryMap));
		ret.put("total", gradeService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加年级信息
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Grade grade){
		Map<String, String> ret = new HashMap<String, String>();
		// 判断所提交的年级名称是否为空
		if(StringUtil.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "年级名称不能为空！");
			return ret;
		}
		if(gradeService.add(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "年级添加失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "年级添加成功！");
		return ret;
	}
	
	/**
	 * 编辑年级信息
	 * @param grade
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Grade grade){
		Map<String, String> ret = new HashMap<String, String>();
		// 判断所提交的年级名称是否为空
		if(StringUtil.isEmpty(grade.getName())) {
			ret.put("type", "error");
			ret.put("msg", "年级名称不能为空！");
			return ret;
		}
		if(gradeService.edit(grade)<=0) {
			ret.put("type", "error");
			ret.put("msg", "年级修改失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "年级修改成功！");
		return ret;
	}
	
	/**
	 * 删除年级信息
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
			ret.put("msg", "请至少选择一条数据进行删除！");
			return ret;
		}
		
		try {
			if(gradeService.delete(com.ischoolbar.programmer.util.StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败！");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "该年级下存在班级信息，无法删除！");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "年级删除成功！");
		return ret;
		
	}
	
}
