package com.service;

import java.util.List;

import com.pojo.Test;

public interface TestService {
	int insert(Test record);
    int insertSelective(Test record);
    List<Test> getTestList();
}
