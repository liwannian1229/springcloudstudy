package com.lwn.demo.service.common;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by wangxudong on 2020/07/06.
 *
 * @version: 1.0
 * @modified :
 */
@Slf4j
@Order
@Component
@Aspect
public class GlobalLogAspect {

//    @Autowired
//    private UserContext userContext;

    @Pointcut("execution(public * com.lwn.demo.service.controller..*.*(..))")
    public void globalLog() {

    }

    @Before("globalLog()")
    public void doBefore(JoinPoint joinPoint) {
//        User user = userContext.getUser();
        log.info("请求日志");
//        if (user != null) {
//            log.info(String.format("UserId: %s", user.getId()));
//        }

        HttpServletRequest request = SessionHolder.getRequest();

        if (request != null) {
            log.info("------request------");
            log.info("URL:" + request.getRequestURL().toString());
            log.info("METHOD:" + request.getMethod());
            log.info("IP:" + request.getRemoteAddr());
            Enumeration<String> enu = request.getParameterNames();
            log.info("url parameter");
            while (enu.hasMoreElements()) {
                String name = enu.nextElement();
                log.info("name:" + name + ",value:" + request.getParameter(name));
            }
            log.info("----request end----");
        }

        if (log.isDebugEnabled()) {
            Object[] args = joinPoint.getArgs();
            log.debug("method signature: " + joinPoint.getSignature());
            if (args != null) {
                for (Object arg : args) {
                    try {
                        log.debug(JSONUtil.toJsonStr(arg));
                    } catch (Exception e) {
                        log.warn(e.getMessage());
                    }
                }
            }
        }
    }

}
