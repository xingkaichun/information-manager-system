package com.xingkaichun.information.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceResult<T> {

    @JsonProperty("ServiceCode")
    private ServiceCode serviceCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Result")
    private T result;

    public ServiceResult(ServiceCode serviceCode, String message, T result) {
        this.serviceCode = serviceCode;
        this.message = message;
        this.result = result;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(ServiceCode serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static<T> ServiceResult<T> createSuccessServiceResult(String message, T result){
        return new ServiceResult(ServiceCode.SUCCESS,message,result);
    }
    public static<T> ServiceResult createFailServiceResult(String message){
        return new ServiceResult(ServiceCode.FAIL,message,null);
    }
}
