package com.lwn.eureka.controller;

import com.lwn.eureka.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudentInfo")
    private String get() {

        return studentService.get();
    }
}
