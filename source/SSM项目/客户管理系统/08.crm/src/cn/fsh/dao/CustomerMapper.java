package cn.fsh.dao;

import java.util.List;

import cn.fsh.domain.Customer;

public interface CustomerMapper {

	/*
	 * ��ѯ��������
	 */
	public List<Customer> findAl();
	
	/*
	 * ��������
	 */
	public void save(Customer customer);

	/*
	 * ����id ��ѯһ������
	 */
	public Customer findById(Integer id);

	public void update(Customer customer);

	public void delete(Integer[] id);
}
