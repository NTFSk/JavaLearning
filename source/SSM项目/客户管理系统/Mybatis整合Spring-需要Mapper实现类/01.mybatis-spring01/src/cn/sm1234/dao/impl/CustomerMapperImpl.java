package cn.sm1234.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

// CustomerMapper的实现类去继承Spring提供的SqlSessionDaoSupport
// 用其里面的getSqlSession()方法可以很方便的拿到一个SqlSession的对象
public class CustomerMapperImpl extends SqlSessionDaoSupport implements CustomerMapper {

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.getSqlSession();
		// 拿到sqlSession后，就可以做一些我们想做的操作
		// 比如增加对象
		sqlSession.insert("saveCustomer",customer);
		/*
		 * 这里不需要进行事务的提交
		 */
	}

}
