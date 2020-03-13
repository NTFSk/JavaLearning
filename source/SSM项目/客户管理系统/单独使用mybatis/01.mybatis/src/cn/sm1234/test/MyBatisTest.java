package cn.sm1234.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

public class MyBatisTest {
	@Test
	public void test() throws IOException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
		// 创建sqlSessionFactory
		SqlSessionFactory factory = builder.build(is);
		SqlSession sqlSession = factory.openSession();
		
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		
		Customer customer = new Customer();
		customer.setName("张三");
		customer.setGender("男");
		customer.setTelephone("123123");
		customer.setAddress("福建厦门");
		
		mapper.saveCustomer(customer);
		
		// 提交事务
		sqlSession.commit();
		// 关闭资源
		sqlSession.close();
	}
}
