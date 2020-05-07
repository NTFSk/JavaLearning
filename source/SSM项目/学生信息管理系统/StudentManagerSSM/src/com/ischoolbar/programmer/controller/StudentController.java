package com.ischoolbar.programmer.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.ischoolbar.programmer.entity.Clazz;
import com.ischoolbar.programmer.entity.Student;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.service.ClazzService;
import com.ischoolbar.programmer.service.StudentService;

import net.sf.json.JSONArray;

/**
 * 学生信息管理
 * @author Crspin
 *
 */
@RequestMapping("/student")
@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private ClazzService clazzService;
	
	
	/**
	 * 学生列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("student/student_list");
		List<Clazz> clazzList = clazzService.findAll();
		model.addObject("clazzList", clazzList);
		model.addObject("clazzListJson", JSONArray.fromObject(clazzList));
		return model;
	}
	
	/**
	 * 获取学生列表
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value = "name",required = false,defaultValue = "") String name,
			@RequestParam(value = "clazzId",required = false) Long clazzId,
			HttpServletRequest request,
			Page page
			){
		/**
		 * ret用来返回后台数据给前端
		 * queryMap用来存放获取到的用户数据
		 */
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", "%"+name+"%");
		Object attribute = request.getSession().getAttribute("userType");
		if("2".equals(attribute.toString())) {
			// 说明是学生
			/**
			 * 从session中取出user属性，如果userType的值为2的话
			 * 那么user属性所对应的就是一个student对象
			 */
			Student loginedStudent = (Student)request.getSession().getAttribute("user");
			queryMap.put("username", "%"+loginedStudent.getUsername()+"%");
		}
		
		if(clazzId != null) {
			queryMap.put("clazzId",clazzId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		/**
		 * datagrid分页操作要求后台返回rows，total参数
		 */
		ret.put("rows", studentService.findList(queryMap));
		ret.put("total", studentService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加学生信息
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Student student){
		Map<String, String> ret = new HashMap<String, String>();
		// 判断所提交的学生姓名是否为空
		if(StringUtil.isEmpty(student.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "学生姓名不能为空！");
			return ret;
		}
		if(StringUtil.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		if(student.getClazzId() == null ) {
			ret.put("type", "error");
			ret.put("msg", "请选择所属班级！");
			return ret;
		}
		if(isExist(student.getUsername(), null)) {
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在！");
			return ret;
		}
		student.setSn(com.ischoolbar.programmer.util.StringUtil.generateSn("S", "N"));
		if(studentService.add(student)<=0) {
			ret.put("type", "error");
			ret.put("msg", "学生添加失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生添加成功！");
		return ret;
	}
	
	/**
	 * 编辑学生信息
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Student student){
		Map<String, String> ret = new HashMap<String, String>();
		// 判断所提交的学生名称是否为空
		if(StringUtil.isEmpty(student.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "学生名称不能为空！");
			return ret;
		}
		if(student.getClazzId() == null) {
			ret.put("type", "error");
			ret.put("msg", "所属班级不能为空！");
			return ret;
		}
		if(isExist(student.getUsername(), student.getId())) {
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在！");
			return ret;
		}
		if(studentService.edit(student)<=0) {
			ret.put("type", "error");
			ret.put("msg", "学生修改失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生修改成功！");
		return ret;
	}
	
	/**
	 * 删除学生信息
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
			if(studentService.delete(com.ischoolbar.programmer.util.StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
				ret.put("type", "error");
				ret.put("msg", "学生删除失败！");
				return ret;
			}
		} catch (Exception e) {
			ret.put("type", "error");
			ret.put("msg", "请勿冲动!");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "学生删除成功！");
		return ret;
		
	}
	
	/**
	 * 图片上传功能
	 * @param photo
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(
			MultipartFile photo,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException{
		Map<String, String> ret = new HashMap<String, String>();
		if(photo == null) {
			// 没有发现上传的文件
			ret.put("type", "error");
			ret.put("msg", "请选择要上传的头像文件!");
			return ret;
		}
		if(photo.getSize() > 10485760) {
			ret.put("type", "error");
			ret.put("msg", "文件大小超过10M，请上传小于10M的图片!");
			return ret;
		}
		// 图片文件名
		String photoName = photo.getOriginalFilename();
		// 文件后缀
		String suffix = photoName.substring(photoName.lastIndexOf(".") + 1,photoName.length());
		if(!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())) {
			ret.put("type", "error");
			ret.put("msg", "格式不正确，请上传jpg,png,gif,jpeg格式的图片!");
			return ret;
		}
		// 头像保存地址
		String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
		// System.out.println(savePath);
		// 指定路径，生成一个File对象，如果路径不存在，则该对象创建失败，所以接下来要对对象是否存在进行判断
		File savePathFile = new File(savePath);
		if(!savePathFile.exists()) {
			savePathFile.mkdir(); // 如果不存在文件，则创建一个文件夹upload
		}
		// 定义文件名
		String filename = new Date().getTime() + "." + suffix;
		// 把用户上传的文件存到该目录下
		photo.transferTo(new File(savePath + filename));
		ret.put("type", "success");
		ret.put("msg", "图片上传成功！");
		ret.put("src", request.getServletContext().getContextPath() + "/upload/" + filename);
		return ret;
	}
	
	private boolean isExist(String username, Long id) {
		Student student = studentService.findByUserName(username);
		// 根据学生名查找到的学生对象不为空时
		if(student != null) {
			// 如果id为空，说明正在新建对象
			if(id == null) {
				return true;
			}
			// 如果两者的id不相等，说明在更改自己的信息
			if(student.getId().longValue() != id.longValue()) {
				return true;
			}
		}
		return false;
	}
	
}
