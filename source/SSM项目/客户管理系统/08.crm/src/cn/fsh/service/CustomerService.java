package cn.fsh.service;

import java.util.List;

import cn.fsh.domain.Customer;

public interface CustomerService {

	/*
	 * 查询所有数据
	 */
	public List<Customer> findAll();

	public void save(Customer customer);

	public Customer findById(Integer id);

	public void delete(Integer[] id);

}
