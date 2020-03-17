package cn.sm1234.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;
import cn.sm1234.service.CustomerService;

// 业务类的实现
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	// 注入Mapper对象
	@Resource
	private CustomerMapper customerMapper;
	
	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerMapper.saveCustomer(customer);
		
		//模拟异常
		int i = 100/0;
		customerMapper.saveCustomer(customer);
	}

}
