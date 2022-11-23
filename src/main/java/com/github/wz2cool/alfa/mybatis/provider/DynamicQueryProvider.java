package com.github.wz2cool.alfa.mybatis.provider;

import com.github.wz2cool.alfa.core.ProviderColumn;
import com.github.wz2cool.alfa.core.ProviderFactory;
import com.github.wz2cool.alfa.core.ProviderTable;
import com.github.wz2cool.alfa.helper.CommonsHelper;
import com.github.wz2cool.alfa.mybatis.mapper.SelectMapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理动态查询
 *
 * @author Frank
 * @author wangjin
 */
public class DynamicQueryProvider {
    private static final Map<String, String> DYNAMIC_QUERY_CACHE = new ConcurrentHashMap<>(256);

    /**
     * 根据{@link SelectProvider#method()}解析的方法
     * 如:
     * {@link SelectMapper}
     *
     * @param providerContext 上下文
     * @return sql脚本
     */
    public String selectAll(ProviderContext providerContext) {
        ProviderTable providerTable = ProviderFactory.create(providerContext);
        if (DYNAMIC_QUERY_CACHE.containsKey(providerTable.getKey())) {
            return DYNAMIC_QUERY_CACHE.get(providerTable.getKey());
        }
        final String sql = "select * from " + providerTable.getTableName();
        DYNAMIC_QUERY_CACHE.put(providerTable.getKey(), sql);
        return sql;
    }

    /**
     * 待实体条件的查询
     *
     * @param providerContext
     * @return
     */
    public String select(ProviderContext providerContext) {
        ProviderTable providerTable = ProviderFactory.create(providerContext);
        if (DYNAMIC_QUERY_CACHE.containsKey(providerTable.getKey())) {
            return DYNAMIC_QUERY_CACHE.get(providerTable.getKey());
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("<script>");
        sqlBuilder.append("select * from ");
        sqlBuilder.append(providerTable.getTableName());
        sqlBuilder.append(" ");
        sqlBuilder.append("<where>");
        for (ProviderColumn column : providerTable.getColumns()) {
            sqlBuilder.append(CommonsHelper.format("<if test=\"%s != null\"> and %s = #{%s}</if>\n",
                    column.getJavaColumn(), column.getDbColumn(), column.getJavaColumn()));
        }
        sqlBuilder.append("</where>");
        sqlBuilder.append("</script>");
        final String sql = sqlBuilder.toString();
        System.out.println(sql);
        DYNAMIC_QUERY_CACHE.put(providerTable.getKey(), sql);
        return sql;
    }

    public String selectByPrimaryKey(ProviderContext providerContext) {
        ProviderTable providerTable = ProviderFactory.create(providerContext);
        if (DYNAMIC_QUERY_CACHE.containsKey(providerTable.getKey())) {
            return DYNAMIC_QUERY_CACHE.get(providerTable.getKey());
        }
        if (providerTable.getPrimaryKey() == null) {
            throw new IllegalArgumentException(CommonsHelper.format("该类[%s]没有发现主键", providerTable.getTableName()));
        }
        final String sql = CommonsHelper.format("select * from %s where %s = #{%s}"
                , providerTable.getTableName(), providerTable.getPrimaryKey().getDbColumn(),
                providerTable.getPrimaryKey().getJavaColumn());
        DYNAMIC_QUERY_CACHE.put(providerTable.getKey(), sql);
        return sql;
    }

}