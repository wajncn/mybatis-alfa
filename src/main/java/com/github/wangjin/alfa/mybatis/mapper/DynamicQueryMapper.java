package com.github.wangjin.alfa.mybatis.mapper;

import java.util.List;

/**
 * @author Frank
 */
public interface DynamicQueryMapper<T> extends
        InsertMapper<T>,
        UpdateMapper<T>,
        DeleteMapper<T>,
        SelectMapper<T>{

}