package com.lwn.repo;

import tk.mybatis.mapper.entity.Example;

public class LwnExample extends Example {

    public LwnExample(Class<?> entityClass) {
        super(entityClass);
    }

    public LwnExample(Class<?> entityClass, boolean exists) {
        super(entityClass, exists);
    }

    public LwnExample(Class<?> entityClass, boolean exists, boolean notNull) {
        super(entityClass, exists, notNull);
    }
}
