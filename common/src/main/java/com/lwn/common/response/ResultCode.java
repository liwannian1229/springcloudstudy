package com.lwn.common.response;

public interface ResultCode {

    // 成功
    String SUCCESS = "0";

    // 失败
    String FAILURE = "1";

    // 无权限
    String NO_AUTH = "3";

    // Token 失效
    String TOKEN_INVALID = "7";

    // 异常
    String EXCEPTION = "9";
}
