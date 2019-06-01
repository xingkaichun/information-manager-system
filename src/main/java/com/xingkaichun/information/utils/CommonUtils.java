package com.xingkaichun.information.utils;

import java.util.Collection;

public class CommonUtils {
    public static boolean isNUllOrEmpty(Object object) {
        if(object == null){
            return true;
        }
        if(object instanceof String){
            String string = (String)object;
            return string.isEmpty();
        }
        if(object instanceof Collection){
            Collection collection = (Collection)object;
            return collection.isEmpty();
        }
        throw new RuntimeException(String.format("不支持类别%s做空判断",object.getClass()));
    }
}
