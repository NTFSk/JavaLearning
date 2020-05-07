package com.ischoolbar.programmer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.ClazzDao;
import com.ischoolbar.programmer.entity.Clazz;
import com.ischoolbar.programmer.service.ClazzService;

@Service
public class ClazzServiceImpl implements ClazzService {

	@Autowired
	private ClazzDao clazzDao;
	
	public int add(Clazz clazz) {
		// TODO Auto-generated method stub
		return clazzDao.add(clazz);
	}

	public int edit(Clazz clazz) {
		// TODO Auto-generated method stub
		return clazzDao.edit(clazz);
	}

	public int delete(String ids) {
		// TODO Auto-generated method stub
		return clazzDao.delete(ids);
	}

	public List<Clazz> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return clazzDao.findList(queryMap);
	}

	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return clazzDao.getTotal(queryMap);
	}

	public List<Clazz> findAll() {
		// TODO Auto-generated method stub
		return clazzDao.findAll();
	}

}
