package cn.sm1234.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sm1234.domain.Customer;
import cn.sm1234.service.CustomerService;

public class MybatisSpringTest {
	
	@Test
	public void test() {
		// 1.加载spring配置
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		CustomerService customerService = (CustomerService)ac.getBean("customerService");
		
		Customer customer = new Customer();
		customer.setName("事务");
		customer.setGender("男");
		customer.setTelephone("123123");
		customer.setAddress("厦门");
		
		customerService.saveCustomer(customer);
	}
}
