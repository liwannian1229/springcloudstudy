package com.lwn.common.model.configuration;

import com.lwn.common.model.exception.GlobalException;
import com.lwn.common.model.exception.InvokeException;
import com.lwn.common.model.exception.NoAuthException;
import com.lwn.common.model.exception.TokenInvalidException;
import com.lwn.common.model.response.ResponseResult;
import com.lwn.common.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
public class GlobalWebMvcConfig implements WebMvcConfigurer {

    // 自定义异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseResult<?>> exceptionHandler(Exception exception) {
        if (exception instanceof GlobalException) {

            if (exception instanceof TokenInvalidException) {
                return new ResponseEntity<>(ResponseResult.failureResult(exception.getMessage(), ResultCode.TOKEN_INVALID), HttpStatus.OK);
            }
            if (exception instanceof NoAuthException) {
                return new ResponseEntity<>(ResponseResult.failureResult(exception.getMessage(), ResultCode.NO_AUTH), HttpStatus.OK);
            }
            if (exception instanceof InvokeException) {
                log.error(exception.getMessage(), exception);
            }

            return new ResponseEntity<>(ResponseResult.failureResult(exception.getMessage()), HttpStatus.OK);
        } else if (exception instanceof MethodArgumentNotValidException) {

            log.warn(exception.getMessage());
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();
            String message = "";
            if (!CollectionUtils.isEmpty(errors)) {
                message = errors.get(0).getDefaultMessage();
            }

            return new ResponseEntity<>(ResponseResult.failureResult(message), HttpStatus.OK);
        }

        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseResult.errorResult("服务器内部异常", exception),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}