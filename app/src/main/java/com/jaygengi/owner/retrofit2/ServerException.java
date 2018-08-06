package com.jaygengi.owner.retrofit2;

/**
 * 描述：服务器下发的错误
 * @author JayGengi
 * @date 2018/8/6 0006 下午 3:51
 */
public class ServerException extends RuntimeException {

    public int code;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
    }
}
