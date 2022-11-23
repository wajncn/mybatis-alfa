package com.github.wz2cool.alfa.mybatis.mapper;

/**
 * @author Frank
 */
public interface DynamicQueryMapper<T> extends
        InsertMapper<T>,
        UpdateMapper<T>,
        DeleteMapper<T>,
        SelectMapper<T> {

}