package cn.fsh.dao;

import java.util.List;

import cn.fsh.domain.Customer;

public interface CustomerMapper {

	/*
	 * 查询所有数据
	 */
	public List<Customer> findAl();
	
	/*
	 * 保存数据
	 */
	public void save(Customer customer);

	/*
	 * 根据id 查询一个对象
	 */
	public Customer findById(Integer id);

	public void update(Customer customer);

	public void delete(Integer[] id);
}
