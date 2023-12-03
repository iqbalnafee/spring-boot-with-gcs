package com.example.bambergBeverageBox.rest;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class RestResponse<T> {

    private Boolean success;
    private Integer code;
    private String message;

    @ToString.Exclude
    private T payload;

    public static <T> RestResponse<T> ofError(String message) {
        return ofError(null, message);
    }

    public static <T> RestResponse<T> ofSuccess(String msg) {
        return ofSuccess(null, msg);
    }

    public static <T> RestResponse<T> ofError(T payload, String msg) {
        return ofError(payload, msg, ResponseCode.BAD_REQUEST, false);
    }

    public static <T> RestResponse<T> ofSuccess(T payload, String msg) {
        return ofError(payload, msg, ResponseCode.OK, true);
    }


    private static <T> RestResponse<T> ofError(T payload, String msg,
                                               ResponseCode responseCode,
                                               Boolean status) {

        return (RestResponse<T>) RestResponse.builder()
                .message(msg)
                .payload(payload)
                .code(responseCode.getErrorCode())
                .success(status)
                .build();
    }
}
