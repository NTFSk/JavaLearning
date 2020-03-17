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
	// ע��service����
	@Resource
	private CustomerService customerService;
	/*
	 * ��ѯ��������
	 * ��ҳ�淵��json��ʽ������
	 * easyui��datagrid�������Ҫչʾ�����ṩjson��ʽ��[{id:1,name:xxx},{id:2,name:xxx}]
	 */
	@RequestMapping("/list")
	@ResponseBody  // ����ת������Ϊjson
	public List<Customer> list(){
		// ��ѯ����
		List<Customer> list = customerService.findAll();
		
		return list;
	}
	
	// ���Map���� �洢��ҳ�������
	private Map<String,Object> result = new HashMap<String,Object>();
	
	/*
	 * ��ҳ��ѯ
	 */
	@RequestMapping("/listByPage")
	@ResponseBody  // ����ת������Ϊjson
	public Map<String,Object> listByPage(Integer page,Integer rows){
		
		// ���÷�ҳ����
		PageHelper.startPage(page,rows);
		// ��ѯ��������
		List<Customer> list = customerService.findAll();
		// ʹ��PageInfo��װ��ѯ���
		PageInfo<Customer> pageInfo = new PageInfo<Customer>(list);
		
		// �ܼ�¼��
		long total = pageInfo.getTotal();
		// ��ǰҳ�����б�
		List<Customer> custList = pageInfo.getList();
		
		result.put("total",total);
		result.put("rows",custList);
		
		return result;
	}
	
	/*
	 * ��������
	 */
	@RequestMapping("/save")
	@ResponseBody  // ����ת������Ϊjson
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
	 * ����ID��ѯ����
	 */
	@RequestMapping("/findById")
	@ResponseBody  // ����ת������Ϊjson
	public Customer findById(Integer id) {
		
		Customer cust = customerService.findById(id);
		return cust;
	}
	
	/*
	 * ɾ������
	 */
	@RequestMapping("/delete")
	@ResponseBody  // ����ת������Ϊjson
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
