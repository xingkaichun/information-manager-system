package com.xingkaichun.information.dto.base;

public class FreshServiceResult<Void> extends  ServiceResult {

    public FreshServiceResult(ServiceCode serviceCode, String message, Object result) {
        super(serviceCode, message, result);
    }
    public static FreshServiceResult createSuccessFreshServiceResult(String message){
        return new FreshServiceResult(ServiceCode.SUCCESS,message,null);
    }
    public static FreshServiceResult createFailFreshServiceResult(String message){
        return new FreshServiceResult(ServiceCode.FAIL,message,null);
    }
}
