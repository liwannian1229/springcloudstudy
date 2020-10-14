package com.lwn.service.service;

import cn.hutool.json.JSONUtil;
import com.lwn.repo.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public String get() {

        return JSONUtil.toJsonStr(studentMapper.selectAll());
    }
}
