package cn.sm1234.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

public class MybatisSpringTest {
	
	@Test
	public void test() {
		// 1.加载spring配置
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 2.获取对象
		CustomerMapper customerMapper = (CustomerMapper)ac.getBean("customerMapper");
		// 3.调用方法
		Customer customer = new Customer();
		customer.setName("测试4");
		customer.setGender("男");
		customer.setTelephone("123123");
		customer.setAddress("厦门");
		
		customerMapper.saveCustomer(customer);
	}
}
