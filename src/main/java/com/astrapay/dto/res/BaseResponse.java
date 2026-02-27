package com.astrapay.dto.res;

import java.util.UUID;

public class BaseResponse<T> {
    private UUID reqID;
    private int code;
    private String status;
    private String message;
    private T data;
    private Object error;

    public BaseResponse(int code, String status, String message, T data, Object error) {
        this.reqID = UUID.randomUUID();
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public static <T> BaseResponse<T> ok(T data, String message) {
        return new BaseResponse<>(200, "T", message, data, null);
    }

    public static <T> BaseResponse<T> created(T data) {
        return new BaseResponse<>(201, "T", "Created Successfully", data, null);
    }

    public static <T> BaseResponse<T> error(int code, String message, Object errorDetails) {
        return new BaseResponse<>(code, "F", message, null, errorDetails);
    }

    public UUID getReqID() { return reqID; }
    public int getCode() { return code; }
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public Object getError() { return error; }
}