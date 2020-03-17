package cn.fsh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.fsh.domain.Customer;
import cn.fsh.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	// 注入service对象
	@Resource
	private CustomerService customerService;
	/*
	 * 查询所有数据
	 * 给页面返回json格式的数据
	 * easyui的datagrid组件，需要展示数据提供json格式：[{id:1,name:xxx},{id:2,name:xxx}]
	 */
	@RequestMapping("/list")
	@ResponseBody  // 用于转换对象为json
	public List<Customer> list(){
		// 查询数据
		List<Customer> list = customerService.findAll();
		
		return list;
	}
	
	// 设计Map集合 存储给页面的数据
	private Map<String,Object> result = new HashMap<String,Object>();
	
	/*
	 * 分页查询
	 */
	@RequestMapping("/listByPage")
	@ResponseBody  // 用于转换对象为json
	public Map<String,Object> listByPage(Integer page,Integer rows){
		
		// 设置分页参数
		PageHelper.startPage(page,rows);
		// 查询所有数据
		List<Customer> list = customerService.findAll();
		// 使用PageInfo封装查询结果
		PageInfo<Customer> pageInfo = new PageInfo<Customer>(list);
		
		// 总记录数
		long total = pageInfo.getTotal();
		// 当前页数据列表
		List<Customer> custList = pageInfo.getList();
		
		result.put("total",total);
		result.put("rows",custList);
		
		return result;
	}
	
	/*
	 * 保存数据
	 */
	@RequestMapping("/save")
	@ResponseBody  // 用于转换对象为json
	public Map<String,Object> save(Customer customer){
		try {
			customerService.save(customer);
			result.put("success", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success",false);
			result.put("msg",e.getMessage());
		}
		return result;
	}
	
	/*
	 * 根据ID查询对象
	 */
	@RequestMapping("/findById")
	@ResponseBody  // 用于转换对象为json
	public Customer findById(Integer id) {
		
		Customer cust = customerService.findById(id);
		return cust;
	}
	
	/*
	 * 删除数据
	 */
	@RequestMapping("/delete")
	@ResponseBody  // 用于转换对象为json
	public Map<String,Object> delete(Integer[] id){
		try {
			customerService.delete(id);
			result.put("success",true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("failed",false);
			result.put("msg",e.getMessage());
		}
		return result;
	}
}
