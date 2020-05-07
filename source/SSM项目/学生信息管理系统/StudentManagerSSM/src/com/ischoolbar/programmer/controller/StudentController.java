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
 * ѧ����Ϣ����
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
	 * ѧ���б�ҳ��
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
	 * ��ȡѧ���б�
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
		 * ret�������غ�̨���ݸ�ǰ��
		 * queryMap������Ż�ȡ�����û�����
		 */
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", "%"+name+"%");
		Object attribute = request.getSession().getAttribute("userType");
		if("2".equals(attribute.toString())) {
			// ˵����ѧ��
			/**
			 * ��session��ȡ��user���ԣ����userType��ֵΪ2�Ļ�
			 * ��ôuser��������Ӧ�ľ���һ��student����
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
		 * datagrid��ҳ����Ҫ���̨����rows��total����
		 */
		ret.put("rows", studentService.findList(queryMap));
		ret.put("total", studentService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * ���ѧ����Ϣ
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Student student){
		Map<String, String> ret = new HashMap<String, String>();
		// �ж����ύ��ѧ�������Ƿ�Ϊ��
		if(StringUtil.isEmpty(student.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "ѧ����������Ϊ�գ�");
			return ret;
		}
		if(StringUtil.isEmpty(student.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ�գ�");
			return ret;
		}
		if(student.getClazzId() == null ) {
			ret.put("type", "error");
			ret.put("msg", "��ѡ�������༶��");
			return ret;
		}
		if(isExist(student.getUsername(), null)) {
			ret.put("type", "error");
			ret.put("msg", "�������Ѵ��ڣ�");
			return ret;
		}
		student.setSn(com.ischoolbar.programmer.util.StringUtil.generateSn("S", "N"));
		if(studentService.add(student)<=0) {
			ret.put("type", "error");
			ret.put("msg", "ѧ�����ʧ�ܣ�");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ѧ����ӳɹ���");
		return ret;
	}
	
	/**
	 * �༭ѧ����Ϣ
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Student student){
		Map<String, String> ret = new HashMap<String, String>();
		// �ж����ύ��ѧ�������Ƿ�Ϊ��
		if(StringUtil.isEmpty(student.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "ѧ�����Ʋ���Ϊ�գ�");
			return ret;
		}
		if(student.getClazzId() == null) {
			ret.put("type", "error");
			ret.put("msg", "�����༶����Ϊ�գ�");
			return ret;
		}
		if(isExist(student.getUsername(), student.getId())) {
			ret.put("type", "error");
			ret.put("msg", "�������Ѵ��ڣ�");
			return ret;
		}
		if(studentService.edit(student)<=0) {
			ret.put("type", "error");
			ret.put("msg", "ѧ���޸�ʧ�ܣ�");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "ѧ���޸ĳɹ���");
		return ret;
	}
	
	/**
	 * ɾ��ѧ����Ϣ
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
			if(studentService.delete(com.ischoolbar.programmer.util.StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
				ret.put("type", "error");
				ret.put("msg", "ѧ��ɾ��ʧ�ܣ�");
				return ret;
			}
		} catch (Exception e) {
			ret.put("type", "error");
			ret.put("msg", "����嶯!");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "ѧ��ɾ���ɹ���");
		return ret;
		
	}
	
	/**
	 * ͼƬ�ϴ�����
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
			// û�з����ϴ����ļ�
			ret.put("type", "error");
			ret.put("msg", "��ѡ��Ҫ�ϴ���ͷ���ļ�!");
			return ret;
		}
		if(photo.getSize() > 10485760) {
			ret.put("type", "error");
			ret.put("msg", "�ļ���С����10M�����ϴ�С��10M��ͼƬ!");
			return ret;
		}
		// ͼƬ�ļ���
		String photoName = photo.getOriginalFilename();
		// �ļ���׺
		String suffix = photoName.substring(photoName.lastIndexOf(".") + 1,photoName.length());
		if(!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())) {
			ret.put("type", "error");
			ret.put("msg", "��ʽ����ȷ�����ϴ�jpg,png,gif,jpeg��ʽ��ͼƬ!");
			return ret;
		}
		// ͷ�񱣴��ַ
		String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
		// System.out.println(savePath);
		// ָ��·��������һ��File�������·�������ڣ���ö��󴴽�ʧ�ܣ����Խ�����Ҫ�Զ����Ƿ���ڽ����ж�
		File savePathFile = new File(savePath);
		if(!savePathFile.exists()) {
			savePathFile.mkdir(); // ����������ļ����򴴽�һ���ļ���upload
		}
		// �����ļ���
		String filename = new Date().getTime() + "." + suffix;
		// ���û��ϴ����ļ��浽��Ŀ¼��
		photo.transferTo(new File(savePath + filename));
		ret.put("type", "success");
		ret.put("msg", "ͼƬ�ϴ��ɹ���");
		ret.put("src", request.getServletContext().getContextPath() + "/upload/" + filename);
		return ret;
	}
	
	private boolean isExist(String username, Long id) {
		Student student = studentService.findByUserName(username);
		// ����ѧ�������ҵ���ѧ������Ϊ��ʱ
		if(student != null) {
			// ���idΪ�գ�˵�������½�����
			if(id == null) {
				return true;
			}
			// ������ߵ�id����ȣ�˵���ڸ����Լ�����Ϣ
			if(student.getId().longValue() != id.longValue()) {
				return true;
			}
		}
		return false;
	}
	
}
