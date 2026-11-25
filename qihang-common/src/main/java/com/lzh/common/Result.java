package com.lzh.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;
    private static final Integer SUCCESS_CODE = 1;
    private static final Integer ERROR_CODE = 0;

    public static Result success(){
        Result result = new Result();
        result.code = SUCCESS_CODE;
        result.msg = "success";
        return result;
    }
    public static Result success(Object data){
        Result result = new Result();
        result.code = SUCCESS_CODE;
        result.msg = "success";
        result.data = data;
        return result;
    }
    public static Result success(String msg,Object data){
        Result result = new Result();
        result.code = SUCCESS_CODE;
        result.msg = msg;
        result.data = data;
        return result;
    }
    public static Result error(String msg){
        Result result = new Result();
        result.code = ERROR_CODE;
        result.msg = msg;
        return result;
    }
}
