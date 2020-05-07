package com.ischoolbar.programmer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.GradeDao;
import com.ischoolbar.programmer.entity.Grade;
import com.ischoolbar.programmer.service.GradeService;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeDao gradeDao;
	
	public int add(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.add(grade);
	}

	public int edit(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.edit(grade);
	}

	public int delete(String ids) {
		// TODO Auto-generated method stub
		return gradeDao.delete(ids);
	}

	public List<Grade> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return gradeDao.findList(queryMap);
	}

	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return gradeDao.getTotal(queryMap);
	}

	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		return gradeDao.findAll();
	}

}
