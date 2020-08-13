package com.cdut.kdchinese.util;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Date: 2020/06/04 22:18
 * Description:
 * Version: V1.0
 * @author iRappelt
 */
@Data
public class Result {

    private Object data;
    private Integer code;
    private String message;

    private Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    private Result(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result failure(ResultCode resultCode) {
        return new Result(resultCode);
    }

    public static Result failure(ResultCode resultCode, Object data) {
        return new Result(resultCode, data);
    }

}
