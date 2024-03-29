package com.github.wz2cool.alfa.helper;

import com.github.wz2cool.alfa.constant.MapperConstants;

/**
 * @author Frank
 * @author wangjin
 */
public class DynamicQuerySqlHelper {
    private static final String bind_template = "<bind name=\"%s\" value=\"@com.github.wz2cool.dynamic.mybatis.mapper.provider.DynamicQueryProvider@%s(%s, %s)\"/>";
    private static final String filter_method = "getDynamicQueryParamInternal";
    private static final String update_method = "getUpdateQueryParamInternal";

    private DynamicQuerySqlHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * bind
     *
     * @param isMapUnderscoreToCamelCase 是否下划线转驼峰
     * @return bind sql
     */
    public static String getBindFilterParams(boolean isMapUnderscoreToCamelCase) {
        return CommonsHelper.format(bind_template, MapperConstants.DYNAMIC_QUERY_PARAMS, filter_method, MapperConstants.DYNAMIC_QUERY, String.valueOf(isMapUnderscoreToCamelCase));
    }

    public static String getUpdateBindFilterParams(boolean isMapUnderscoreToCamelCase) {
        return CommonsHelper.format(bind_template, MapperConstants.DYNAMIC_QUERY_PARAMS, update_method, MapperConstants.DYNAMIC_QUERY, String.valueOf(isMapUnderscoreToCamelCase));
    }

    public static String getSelectColumnsClause() {
        return CommonsHelper.format(" ${%s.%s} ", MapperConstants.DYNAMIC_QUERY_PARAMS, MapperConstants.SELECT_COLUMNS_EXPRESSION);
    }

    /**
     * 获取where条件
     *
     * @param entityClass 实体对象
     * @return 动态条件
     */
    public static String getWhereClause(Class<?> entityClass) {
        final String newExpression = CommonsHelper.format("%s.%s", MapperConstants.DYNAMIC_QUERY_PARAMS, MapperConstants.WHERE_EXPRESSION);
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        sql.append(CommonsHelper.format("<if test=\"%s != null and %s != ''\">${%s}</if>",
                newExpression, newExpression, newExpression));
        sql.append("</where>");
        return sql.toString();
    }

    public static String getSortClause() {
        String newExpression = String.format("%s.%s", MapperConstants.DYNAMIC_QUERY_PARAMS, MapperConstants.SORT_EXPRESSION);
        return CommonsHelper.format("<if test=\"%s != null and %s != ''\">ORDER BY ${%s}</if>",
                newExpression, newExpression, newExpression);
    }

    public static String getSetClause() {
        String newExpression = CommonsHelper.format("%s.%s", MapperConstants.DYNAMIC_QUERY_PARAMS, MapperConstants.SET_EXPRESSION);
        return CommonsHelper.format("<if test=\"%s != null and %s != ''\">SET ${%s}</if>",
                newExpression, newExpression, newExpression);
    }

    public static String getSelectMax() {
        return CommonsHelper.format("SELECT MAX(${%s})", MapperConstants.COLUMN);
    }

    public static String getSelectMin() {
        return CommonsHelper.format("SELECT MIN(${%s})", MapperConstants.COLUMN);
    }

    public static String getSelectSum() {
        return CommonsHelper.format("SELECT SUM(${%s})", MapperConstants.COLUMN);
    }

    public static String getSelectAvg() {
        return CommonsHelper.format("SELECT AVG(${%s})", MapperConstants.COLUMN);
    }
}