package com.dao;

import java.util.List;

import com.pojo.Test;

public interface TestMapper {
    int insert(Test record);

    int insertSelective(Test record);
    
    List<Test> getTestList();
    
}