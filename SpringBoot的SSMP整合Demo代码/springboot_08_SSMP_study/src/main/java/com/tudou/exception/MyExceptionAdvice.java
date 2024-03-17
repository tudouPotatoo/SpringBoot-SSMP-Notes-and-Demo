package com.tudou.exception;

import com.tudou.controller.model.Result;
import com.tudou.utils.enumeration.StatusMsg;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 相当于 @ResponseBody + @ControllerAdvice
@RestControllerAdvice
public class MyExceptionAdvice {
    @ExceptionHandler({Exception.class})
    public Result handlerException(Exception ex) {
        // 输出错误信息
        ex.printStackTrace();
        // 向前端返回数据
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR.value(), StatusMsg.ERROR.toString());
    }
}
