package com.jaygengi.owner.utils.eventbus;

/**
 * 描述：EventBus事件对象
 * @author JayGengi
 * @date 2018/8/6 0006 下午 4:14
 */
public class Event<T> {
    private int code;
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
