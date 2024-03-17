package com.tudou.controller.model;

import com.tudou.utils.enumeration.StatusMsg;
import lombok.Data;

/**
 * 统一给前端返回的数据格式
 */
@Data
public class Result {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 业务处理后的数据
     */
    private Object data;
    /**
     * 执行情况信息
     * 如果服务器出故障 可以通过msg返回服务器的情况
     */
    private String msg;

    public Result() {
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, StatusMsg msg) {
        this.code = code;
        this.msg = msg.toString();
    }

    public Result(Integer code, Object data, StatusMsg msg) {
        this.code = code;
        this.data = data;
        this.msg = msg.toString();
    }
}
