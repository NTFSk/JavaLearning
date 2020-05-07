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
 * 用户（管理员）控制器
 * @author Crspin
 *
 */
@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	public UserService userService;
	/**
	 *  用户管理列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/user_list");
		return model;
	}
	
	/**
	 * 获取用户列表
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
		 * ret用来返回后台数据给前端
		 * queryMap用来存放获取到的用户数据
		 */
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", "%"+username+"%");
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		/**
		 * datagrid分页操作要求后台返回rows，total参数
		 */
		ret.put("rows", userService.findList(queryMap));
		ret.put("total", userService.getTotal(queryMap));
		return ret;
	}
	
	
	/**
	 * 添加用户操作
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		// 用于存放返回值的容器
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type","error");
			ret.put("msg", "数据绑定出错，请联系管理员！");
			return ret;
		}
		// 检验用户名是否为空
		if(StringUtils.isNullOrEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "用户名不能为空！");
			return ret;
		}
		// 检验密码是否为空
		if(StringUtils.isNullOrEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		User exitUser = userService.findByUserName(user.getUsername());
		if(exitUser != null) {
			ret.put("type","error");
			ret.put("msg", "用户名已经存在！");
			return ret;
		}
		if(userService.add(user) <= 0) {
			ret.put("type","error");
			ret.put("msg", "添加失败！");
			return ret;
		}
		ret.put("type","success");
		ret.put("msg", "添加成功！");
		return ret;
	}
	
	/**
	 * 编辑用户操作
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
		// 用于存放返回值的容器
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null) {
			ret.put("type","error");
			ret.put("msg", "数据绑定出错，请联系管理员！");
			return ret;
		}
		// 检验用户名是否为空
		if(StringUtils.isNullOrEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "用户名不能为空！");
			return ret;
		}
		// 检验密码是否为空
		if(StringUtils.isNullOrEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		// 根据选中的用户的用户名来获取User对象
		User exitUser = userService.findByUserName(user.getUsername());
		/**
		 * 假设该用户是为了修改密码，那么exitUser必然存在
		 * 这时候要去检测exitUser的id和被选中的那一条用户数据的id是否相同，如果相同，则这是同一个用户，可以进行修改
		 * 如果不同，则说明数据库中已经存在别的用户占用了这个用户名
		 * 那么就要提示“用户名已存在”
		 */
		if(exitUser != null) {
			if(user.getId() != exitUser.getId()) {
				ret.put("type","error");
				ret.put("msg", "用户名已经存在！");
				return ret;
			}
			
		}
		if(userService.edit(user) <= 0) {
			ret.put("type","error");
			ret.put("msg", "添加失败！");
			return ret;
		}
		ret.put("type","success");
		ret.put("msg", "添加成功！");
		return ret;
	}
	
	/**
	 * 批量删除操作
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestParam(value="ids[]", required = true) Long[] ids
			){
		// 用于存放返回值的容器
		Map<String, String> ret = new HashMap<String, String>();
		// 判断删除按钮被点击时有没有数据被选中
		if(ids == null) {
			ret.put("type","error");
			ret.put("msg", "请选择要删除的数据！");
			return ret;
		}
		/**
		 * 把被选中的用户的id变成 "(1，2，3)"的形式
		 */
		String idsString = "";
		for(Long id:ids) {
			idsString += id + ",";
		}
		idsString = idsString.substring(0,idsString.length()-1);
		if(userService.delete(idsString)<=0) {
			ret.put("type","error");
			ret.put("msg", "删除失败！");
			return ret;
		}
		ret.put("type","success");
		ret.put("msg", "删除成功！");
		return ret;
	}
}
