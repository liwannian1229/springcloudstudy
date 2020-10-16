package com.lwn.demo.service.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * SessionHolder
 */
public class SessionHolder {
    private SessionHolder() {
    }

    public static HttpSession getSession() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert null != sra;
        HttpServletRequest request = sra.getRequest();
        return request.getSession(true);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == sra) {
            return null;
        }
        return sra.getRequest();
    }

    public static String getRemoteIp() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == sra) {
            return null;
        }
        String ip = sra.getRequest().getRemoteHost();
        String localIp = "127.0.0.1";
        String localHost = "localhost";
        if (StrUtil.isEmpty(ip) || ip.contains(localIp) || ip.contains(localHost)) {
            ip = sra.getRequest().getHeader("Host");
            if (StrUtil.isEmpty(ip) || ip.contains(localIp) || ip.contains(localHost)) {
                ip = sra.getRequest().getHeader("X-Real_IP");
                if (StrUtil.isEmpty(ip) || ip.contains(localIp) || ip.contains(localHost)) {
                    ip = sra.getRequest().getHeader("Remote_Addr");
                }
            }
        }

        return ip;
    }

}
