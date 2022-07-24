package com.xxx.securityjwt.common;


import com.xxx.securityjwt.common.exception.ExceptionEnum;

/**
 * 通用返回对象
 */
public class ApiRestResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    private static final int OK_CODE=200;

    private static final String OK_MSG="SUCCESS";

    public ApiRestResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    //当调用无参构造时默认调用两参构造方法
    public ApiRestResponse() {
        this(OK_CODE,OK_MSG);
    }
    /**
     *无data数据的success方法
     * @param <T> data的数据类型，没有就不用写
     * @return ApiRestResponse对象，包含OK_CODE=10000 和 OK_MSG="SUCCESS"信息
     */
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }
    /**
     *有data数据的success方法
     * @param result data数据
     * @param <T> data类型
     * @return ApiRestResponse对象，包含OK_CODE=10000 、 OK_MSG="SUCCESS"和data信息
     */
    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response = new ApiRestResponse<T>();
        response.setData(result);
        return response;
    }
    /**
     * @param code error状态码
     * @param msg error信息
     * @param <T>
     * @return ApiRestResponse对象的error信息
     */
    public static <T> ApiRestResponse<T> error(Integer code,String msg){
        return new ApiRestResponse<>(code,msg);
    }
    /**
     *
     * @param ex MallExceptionEnum枚举类,MallExceptionEnum类定义看第二点的代码
     * @param <T>
     * @return ApiRestResponse对象的error信息
     */
    public static <T> ApiRestResponse<T> error(ExceptionEnum ex){
        return new ApiRestResponse<>(ex.getCode(),ex.getMsg());
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
