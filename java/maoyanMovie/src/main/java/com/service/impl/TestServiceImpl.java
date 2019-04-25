package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pojo.Test;
import com.service.TestService;
import com.dao.TestMapper;

@Transactional
@Service
public class TestServiceImpl implements TestService {

	@Autowired
	TestMapper testMapper;
	
	public int insert(Test record) {
		// TODO Auto-generated method stub
		return testMapper.insert(record);
	}

	public int insertSelective(Test record) {
		// TODO Auto-generated method stub
		return testMapper.insertSelective(record);
	}

	public List<Test> getTestList() {
		// TODO Auto-generated method stub
		return testMapper.getTestList();
	}

}
