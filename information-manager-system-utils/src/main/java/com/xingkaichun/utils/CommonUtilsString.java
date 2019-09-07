package com.xingkaichun.utils;

public class CommonUtilsString {
    public static boolean isNullOrEmpty(String str){
        if(str == null){
            return true;
        }
        if("".equals(str.trim())){
            return true;
        }
        return false;
    }

}
