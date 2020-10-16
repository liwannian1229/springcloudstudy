package com.lwn.demo.service.service;

import com.alibaba.fastjson.TypeReference;
import com.lwn.common.request.PageCondition;
import com.lwn.common.response.Paging;
import com.lwn.common.util.JsonUtil;
import com.lwn.common.util.QueryHelper;
import com.lwn.demo.service.common.RedisCacheService;
import com.lwn.repo.entity.Student;
import com.lwn.repo.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisCacheService redisCacheService;

    public Paging<Student> get(PageCondition pageCondition) {
        List<Student> students = studentMapper.selectAll();
        redisCacheService.set("students", students, 600);
        QueryHelper.setupPageCondition(pageCondition);

        return QueryHelper.getPaging(students);
    }

    public List<Student> getStudentList() {
        String students = redisCacheService.getString("students", 600);

        return JsonUtil.fromJson_Alibaba(students, new TypeReference<List<Student>>() {
        });
    }
}
