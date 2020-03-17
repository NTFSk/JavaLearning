package cn.fsh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fsh.dao.CustomerMapper;
import cn.fsh.domain.Customer;
import cn.fsh.service.CustomerService;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	// 注入一个Mapper接口对象
	@Resource
	private CustomerMapper customerMapper;
	
	public List<Customer> findAll() {
		return customerMapper.findAl();
	}

	public void save(Customer customer) {
		// TODO Auto-generated method stub
		// 如果用户id不为空，则是修改
		// 为空则是新增
		if(customer.getId()!=null) {
			customerMapper.update(customer);
		}else {
			customerMapper.save(customer);
		}
	}

	public Customer findById(Integer id) {
		return customerMapper.findById(id);
	}

	// 根据id删除
	public void delete(Integer[] id) {
		// TODO Auto-generated method stub
		customerMapper.delete(id);
	}
	

}
