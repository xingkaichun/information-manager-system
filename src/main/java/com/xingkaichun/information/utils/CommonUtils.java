package com.xingkaichun.information.utils;

import java.util.*;
import java.util.stream.Stream;

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

    public static boolean isNUll(Object object) {
        return object == null;
    }

    public static<T> List<T> Stream2List(Stream<T> stream){
        if(stream == null){
            return null;
        }
        List<T> list = new ArrayList<T>(Integer.valueOf(String.valueOf(stream.count())));
        stream.forEach(t -> list.add(t));
        return list;
    }

    public static<T> Set<T> Stream2Set(Stream<T> stream){
        if(stream == null){
            return null;
        }
        Set<T> set = new HashSet<>();
        stream.forEach(t -> set.add(t));
        return set;
    }
}
