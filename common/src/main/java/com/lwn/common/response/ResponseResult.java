package com.lwn.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@ApiModel(value = "ResponseResult", description = "基础响应数据")
public class ResponseResult<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String message = "";

    private boolean success;

    private String code;

    /**
     * 返回成功结果
     *
     * @param data
     * @return
     */
    public static <T> ResponseResult<T> successResult(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setData(data);
        result.success = true;
        result.code = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 成功结果
     *
     * @return
     */
    public static <T> ResponseResult<T> successResult() {
        ResponseResult<T> result = new ResponseResult<>();
        result.success = true;
        result.code = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 成功结果
     *
     * @return
     */
    public static <T> ResponseResult<T> successResult(T data, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.success = true;
        result.data = data;
        result.message = message;
        result.code = ResultCode.SUCCESS;
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param message
     * @return
     */
    public static <T> ResponseResult<T> failureResult(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setMessage(message);
        result.setCode(ResultCode.FAILURE);
        result.success = false;
        return result;
    }

    /**
     * 返回失败的结果
     *
     * @param message
     * @return
     */
    public static <T> ResponseResult<T> failureResult(String message, String code) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setMessage(message);
        result.setCode(code);
        result.success = false;
        return result;
    }

    /**
     * 异常结果
     *
     * @param message
     * @param exception
     * @return
     */
    public static <T> ResponseResult<T> errorResult(String message, Exception exception) {
        ResponseResult<T> result = new ResponseResult<>();
        result.success = false;
        result.setCode(ResultCode.EXCEPTION);
        result.setMessage(message);
        return result;
    }

    /**
     * 返回异常结果
     *
     * @param code
     * @param message
     * @param exception
     * @return
     */
    public static <T> ResponseResult<T> errorResult(String message, Exception exception, String code) {
        log.error("", exception);
        ResponseResult<T> result = new ResponseResult<>();
        result.setMessage(message + "Error:" + exception.getMessage());
        result.setCode(code);
        result.success = false;
        return result;
    }

}
