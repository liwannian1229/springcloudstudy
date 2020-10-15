package com.lwn.demo.service.controller;

import com.lwn.demo.service.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/student")
@Api(value = "学生信息类")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudentInfo")
    @ApiOperation(value = "获取方法")
    private String get() {

        return studentService.get();
    }
}
