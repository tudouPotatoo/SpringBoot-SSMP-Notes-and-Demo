package com.tudou.utils.enumeration;

public enum StatusMsg {
    SUCCESS("操作成功"),
    ERROR("服务器出错，请稍候");

    private final String message;

    StatusMsg(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
