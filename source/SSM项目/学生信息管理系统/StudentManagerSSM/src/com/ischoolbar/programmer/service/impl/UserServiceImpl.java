package com.ischoolbar.programmer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.UserDao;
import com.ischoolbar.programmer.entity.User;
import com.ischoolbar.programmer.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	public User findByUserName(String username) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(username);
	}

	public int add(User user) {
		// TODO Auto-generated method stub
		return userDao.add(user);
	}

	public List<User> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return userDao.findList(queryMap);
	}

	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return userDao.getTotal(queryMap);
	}

	public int edit(User user) {
		// TODO Auto-generated method stub
		return userDao.edit(user);
	}

	public int delete(String ids) {
		// TODO Auto-generated method stub
		return userDao.delete(ids);
	}

}
