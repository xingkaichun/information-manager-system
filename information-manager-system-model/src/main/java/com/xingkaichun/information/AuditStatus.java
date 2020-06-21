package com.xingkaichun.information;

/**
 * 审核状态
 * @author 邢开春 xingkaichun@qq.com
 */
public enum AuditStatus {

    NON_AUDIT(0,"未审核")
    ,PASS(1,"审核通过")
    ,NOT_PASS(2,"审核未通过");

    int code;
    String describle;

    AuditStatus(int code,String describle){
        this.code = code;
        this.describle = describle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }
}
