package com.lwn.demo.service.common;

import cn.hutool.core.util.StrUtil;
import com.lwn.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheService implements CacheService {

    private static final long NOT_EXPIRE = -1;

    @Value("${my.key-prefix}")
    String keyPrefix;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HashOperations<String, String, Object> hashOps;

    @Autowired
    private ValueOperations<String, Object> valueOps;

    @Autowired
    private ListOperations<String, Object> listOps;

    @Autowired
    private SetOperations<String, Object> setOps;

    @Autowired
    private ZSetOperations<String, Object> zSetOps;

    public void set(String key, Object value, long expire) {
        valueOps.set(prefixed(key), JsonUtil.toJson_Google(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
    }

    public void setNoPrefix(String key, Object value, long expire) {
        valueOps.set(key, JsonUtil.toJson_Google(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public Boolean hasKey(String key) {

        return redisTemplate.hasKey(prefixed(key));
    }

    public void set(String key, Object value) {
        set(key, value, NOT_EXPIRE);
    }

    public void expire(String key, Long expire, TimeUnit timeUnit) {
        redisTemplate.expire(prefixed(key), expire, timeUnit);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(prefixed(key));
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        Object value = valueOps.get(prefixed(key));

        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }

        if (null == value) {

            return null;
        }

        if (clazz.isPrimitive()) {

            return (T) value;
        }

        try {
            if (value instanceof Number) {
                Method method = clazz.getMethod("valueOf", String.class);
                return clazz.cast(method.invoke("valueOf", value.toString()));
            }
        } catch (Exception ignored) {
        }

        return clazz.cast(value);
    }

    public <T> T getNoPrefix(String key, Class<T> clazz, long expire) {
        Object value = valueOps.get(key);

        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }

        if (null == value) {

            return null;
        }

        if (clazz.isPrimitive()) {

            return (T) value;
        }

        try {
            if (value instanceof Number) {
                Method method = clazz.getMethod("valueOf", String.class);
                return clazz.cast(method.invoke("valueOf", value.toString()));
            }
        } catch (Exception ignored) {
        }

        return clazz.cast(value);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    /**
     * 设置值，但是不会重置过期时间
     *
     * @param key
     * @param value
     */
    public void update(String key, Object value) {
        Long expire = redisTemplate.getExpire(prefixed(key), TimeUnit.SECONDS);

        // 1秒的延迟
        if (null != expire && expire > 1) {
            redisTemplate.opsForValue().set(prefixed(key), value, expire, TimeUnit.SECONDS);
        }
    }

    public String getString(String key, long expire) {
        Object value = valueOps.get(prefixed(key));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
        if (null == value) {
            return null;
        }
        return value.toString();
    }

    public String getString(String key) {
        return getString(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(prefixed(key));
    }

    /**
     * 删除key开头的所有数据
     *
     * @param key
     */
    public void deleteKeys(String key) {
        Set<String> keys = redisTemplate.keys(prefixed(key) + "*");
        assert null != keys;
        redisTemplate.delete(keys);
    }

    public void deleteNoPrefixedKeys(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        assert null != keys;
        redisTemplate.delete(keys);
    }


    private String prefixed(String key) {
        return StrUtil.isEmpty(keyPrefix) ? key : keyPrefix + "_" + key;
    }


    public Long leftPush(String key, Object value) {
        return leftPush(key, value, NOT_EXPIRE);
    }

    /**
     * 从key列表左边（从头部）插入值
     *
     * @param key    键（不能为空）
     * @param value  插入列表的值
     * @param expire 失效时间（秒）
     * @return 返回列表插入后的长度
     */
    public Long leftPush(String key, Object value, long expire) {
        Long size = listOps.leftPush(prefixed(key), value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
        return size;
    }

    public Long leftPushAll(String key, Collection<Object> collection) {
        return leftPushAll(key, collection, NOT_EXPIRE);
    }

    public Long leftPushAll(String key, Collection<Object> collection, long expire) {
        Long size = listOps.leftPushAll(prefixed(key), collection);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(prefixed(key), expire, TimeUnit.SECONDS);
        }
        return size;
    }

    /**
     * 获取指定key的范围内的value值的List列表
     * （0，-1）反回所有值列表
     * 0 表示列表的第一个元素，
     * 1 表示列表的第二个元素，以此类推。
     * -1 表示列表的最后一个元素，
     * -2 表示列表的倒数第二个元素，以此类推。
     */
    public List<Object> range(String key, long start, long end) {
        return listOps.range(prefixed(key), start, end);
    }

    /**
     * 删除列表中第一个遇到的value值，count指定删除多少个
     *
     * @param key   键（不能为空）
     * @param count 删除个数
     * @param value 删除的值
     * @return 返回删除后列表长度
     */
    public Long remove(String key, long count, Object value) {
        return listOps.remove(prefixed(key), count, value);
    }

    /**
     * 移除列表中的第一个值，并返回该值
     *
     * @param key 键（不能为空）
     * @return 移除的值
     */
    public Object leftPop(String key) {
        return listOps.leftPop(prefixed(key));
    }

    /**
     * 移除列表的第一个值，并且返回（阻塞版本的leftPop(K key)）
     * 如果列表为空，则一直阻塞指定的时间单位
     *
     * @param key      键（不能为空）
     * @param timeout  阻塞时间
     * @param timeUnit 时间单位
     * @return 移除的值
     */
    public Object leftPop(String key, long timeout, TimeUnit timeUnit) {
        return listOps.leftPop(prefixed(key), timeout, timeUnit);
    }

    /**
     * 获取key列表的长度
     *
     * @param key 键（不能为空）
     * @return 列表的长度
     */
    public Long size(String key) {
        return listOps.size(prefixed(key));
    }
}

