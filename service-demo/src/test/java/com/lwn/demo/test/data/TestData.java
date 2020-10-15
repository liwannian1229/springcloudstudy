package com.lwn.demo.test.data;

import cn.hutool.json.JSONUtil;
import com.lwn.repo.mapper.StudentMapper;
import com.lwn.demo.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestData extends BaseTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testA() {
        System.out.println(JSONUtil.toJsonStr(studentMapper.selectAll()));
    }
}
