package com.lwn.demo.service.controller;

import com.lwn.common.request.PageCondition;
import com.lwn.common.response.Paging;
import com.lwn.common.response.ResponseResult;
import com.lwn.demo.service.service.StudentService;
import com.lwn.repo.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
@Api(value = "学生信息类")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/getStudentInfo")
    @ApiOperation(value = "获取方法")
    private ResponseResult<Paging<Student>> get(@Validated @RequestBody PageCondition pageCondition) {

        return ResponseResult.successResult(studentService.get(pageCondition));
    }

    @GetMapping("/getStudent")
    @ApiOperation(value = "拿到缓存值")
    private ResponseResult<List<Student>> getStudent() {

        return ResponseResult.successResult(studentService.getStudentList());
    }
}
