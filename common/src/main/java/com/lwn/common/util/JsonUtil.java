package com.lwn.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;

/**
 * @author liwannian
 * @date 2020/10/16 20:17
 */
public class JsonUtil {

    private static final Gson gson = new Gson();

    public static String toJson_Google(Object o) {

        return gson.toJson(o);
    }

    public static <T> T fromJson_Google(String json, Class<T> clazz) {

        return gson.fromJson(json, clazz);
    }

    public static String toJson_Alibaba(Object o) {

        return JSON.toJSONString(o);
    }

    public static <T> T fromJson_Alibaba(String json, Class<T> clazz) {

        return JSON.parseObject(json, clazz);
    }

    public static <T> T fromJson_Alibaba(String json, TypeReference<T> reference) {

        return JSON.parseObject(json, reference);
    }

}
