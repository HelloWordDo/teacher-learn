package com.teacher.teacherlearn.curl_14_5.common;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalCache {

    ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap<>();

    public Object getValue(String key) {
        return cacheMap.get(key);
    }

    public void put(String key, Object value) {
        cacheMap.put(key, value);
    }


}
