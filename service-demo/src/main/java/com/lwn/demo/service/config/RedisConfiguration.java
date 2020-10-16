package com.lwn.demo.service.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NullValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.TimeZone;

@Configuration
@EnableCaching
public class RedisConfiguration {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(createGenericObjectMapper()));
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(createGenericObjectMapper()));

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    private ObjectMapper createGenericObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        objectMapper.registerModule(new SimpleModule().addSerializer(new StdSerializer<NullValue>(NullValue.class) {
            private String classIdentifier;

            @Override
            public void serialize(NullValue value, JsonGenerator jsonGenerator, SerializerProvider provider)
                    throws IOException {
                classIdentifier = StringUtils.hasText(classIdentifier) ? classIdentifier : "@class";
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(classIdentifier, NullValue.class.getName());
                jsonGenerator.writeEndObject();
            }
        }));


        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        return objectMapper;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(createGenericObjectMapper());
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                jackson2JsonRedisSerializer));
        return configuration;
    }

    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Bean
    public GeoOperations<String, Object> geoOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForGeo();
    }

    @Bean
    public HyperLogLogOperations<String, Object> hyperLogLogOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHyperLogLog();
    }

}
