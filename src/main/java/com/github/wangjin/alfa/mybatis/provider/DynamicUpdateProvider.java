package com.github.wangjin.alfa.mybatis.provider;

import com.github.wangjin.alfa.core.DynamicCreateSqlFactory;
import com.github.wangjin.alfa.core.ProviderFactory;
import com.github.wangjin.alfa.core.ProviderTable;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Frank
 */
public class DynamicUpdateProvider {
    private static final Map<String, String> DYNAMIC_QUERY_CACHE = new ConcurrentHashMap<>(256);

    public String updateByPrimaryKey(ProviderContext providerContext) {
        ProviderTable providerTable = ProviderFactory.create(providerContext);
        if (DYNAMIC_QUERY_CACHE.containsKey(providerTable.getKey())) {
            return DYNAMIC_QUERY_CACHE.get(providerTable.getKey());
        }
        final String sql = DynamicCreateSqlFactory.getSqlFactory(providerTable).getUpdateByPrimaryKeySql(false);
        DYNAMIC_QUERY_CACHE.put(providerTable.getKey(), sql);
        return sql;
    }

    public String updateByPrimaryKeySelective(ProviderContext providerContext) {
        ProviderTable providerTable = ProviderFactory.create(providerContext);
        if (DYNAMIC_QUERY_CACHE.containsKey(providerTable.getKey())) {
            return DYNAMIC_QUERY_CACHE.get(providerTable.getKey());
        }
        final String sql = DynamicCreateSqlFactory.getSqlFactory(providerTable).getUpdateByPrimaryKeySql(true);
        DYNAMIC_QUERY_CACHE.put(providerTable.getKey(), sql);
        return sql;
    }
}