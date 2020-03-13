package cn.sm1234.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import cn.sm1234.dao.CustomerMapper;
import cn.sm1234.domain.Customer;

// CustomerMapper��ʵ����ȥ�̳�Spring�ṩ��SqlSessionDaoSupport
// ���������getSqlSession()�������Ժܷ�����õ�һ��SqlSession�Ķ���
public class CustomerMapperImpl extends SqlSessionDaoSupport implements CustomerMapper {

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = this.getSqlSession();
		// �õ�sqlSession�󣬾Ϳ�����һЩ���������Ĳ���
		// �������Ӷ���
		sqlSession.insert("saveCustomer",customer);
		/*
		 * ���ﲻ��Ҫ����������ύ
		 */
	}

}
