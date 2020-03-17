package cn.sm1234.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

public class MybatisSpringTest {
	
	@Test
	public void test() {
		// 1.����spring����
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 2.��ȡ����
		CustomerMapper customerMapper = (CustomerMapper)ac.getBean("customerMapper");
		// 3.���÷���
		Customer customer = new Customer();
		customer.setName("����4");
		customer.setGender("��");
		customer.setTelephone("123123");
		customer.setAddress("����");
		
		customerMapper.saveCustomer(customer);
	}
}
