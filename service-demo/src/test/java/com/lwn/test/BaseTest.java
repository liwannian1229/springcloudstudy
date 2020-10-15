package com.lwn.test;

import com.lwn.demo.service.ServiceDemoApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceDemoApp.class)// 指定spring-boot的启动类
@Transactional
public abstract class BaseTest {
}
