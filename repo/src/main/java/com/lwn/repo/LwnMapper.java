package com.lwn.repo;


import tk.mybatis.mapper.common.*;

public interface LwnMapper<T> extends Mapper<T>, MySqlMapper<T>, IdsMapper<T>, ExampleMapper<T>, ConditionMapper<T>, RowBoundsMapper<T> {
}
