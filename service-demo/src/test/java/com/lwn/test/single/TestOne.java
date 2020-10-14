package com.lwn.test.single;

import com.lwn.repo.mapper.StudentMapper;
import com.lwn.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestOne extends BaseTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testA() {
        System.out.println(studentMapper.selectAll());
    }
}
