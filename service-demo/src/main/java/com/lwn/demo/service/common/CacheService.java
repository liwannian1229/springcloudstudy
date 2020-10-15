package com.lwn.demo.service.common;

public interface CacheService {

    void set(String key, Object value, long expire);

    void set(String key, Object value);

    <T> T get(String key, Class<T> clazz);

    void delete(String key);

    void deleteKeys(String key);
}
