package com.jaygengi.owner.retrofit2;

import android.content.Context;


/**
 * 描述：请求头
 * @author JayGengi
 * @date 2018/8/6 0006 下午 3:49
 */
public class ResponseHead<T> {

    /**
     * 接口名称
     * */
    private String method;
    /**
     * 信息级别：Info、警告：Warn、异常：Error
     * */
    private String level;
    /**
     * 操作结果：000、001、002、003
     * */
    private String code;
    /**
     * 返回信息
     * */
    private String description;

    /**
     * Gank.io回调状态
     * "error":false成功
     * */
    private String error;
    /**
     * 数据集
     * */
    private T data;
    @Override
    public String toString() {
        return "ResponseHead{" +
                "method='" + method + '\'' +
                ", level='" + level + '\'' +
                ", code='" + code + '\'' +
                ", error='" + error + '\'' +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isOk(Context context) {
        if (error.equals("false")) {
            return true;
        } else {
            NetworkError.error(context, new ServerException(Integer.parseInt(code), description));
            return false;
        }
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
