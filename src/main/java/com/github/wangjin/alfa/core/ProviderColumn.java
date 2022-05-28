package com.github.wangjin.alfa.core;

import javax.persistence.Column;
import java.lang.reflect.Field;

/**
 * @author wangjin
 */
public final class ProviderColumn {
    private final Field field;
    /**
     * java字段
     */
    private final String javaColumn;
    /**
     * 数据库字段
     * {@link Column#name()}
     */
    private final String dbColumn;

    /**
     * 数据库插入字段 该属性会去掉 .
     * {@link Column#name()}
     */
    private final String dbInsertColumn;

    /**
     * 数据库字段对应哪个表
     * {@link Column#table()}
     */
    private final String dbColumnTable;
    /**
     * 是否为主键
     */
    private final boolean isPrimaryKey;
    /**
     * java字段的类型
     */
    private final Class<?> columnType;

    public ProviderColumn(Field field, String javaColumn, String dbColumn,
                          String dbColumnTable, boolean isPrimaryKey,
                          Class<?> columnType,
                          String dbInsertColumn) {
        this.field = field;
        this.javaColumn = javaColumn;
        this.dbColumn = dbColumn;
        this.dbColumnTable = dbColumnTable;
        this.isPrimaryKey = isPrimaryKey;
        this.columnType = columnType;
        this.dbInsertColumn = dbInsertColumn;
    }

    public static ProviderColumnBuilder builder() {
        return new ProviderColumnBuilder();
    }

    public String getDbInsertColumn() {
        return dbInsertColumn;
    }

    public String getDbColumnTable() {
        return dbColumnTable;
    }

    public String getJavaColumn() {
        return javaColumn;
    }

    public String getDbColumn() {
        return dbColumn;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public Class<?> getColumnType() {
        return columnType;
    }

    public Field getField() {
        return field;
    }

    public static final class ProviderColumnBuilder {
        private Field field;
        private String javaColumn;
        private String dbColumn;
        private String dbInsertColumn;
        private String dbColumnTable;
        private boolean isPrimaryKey;
        private Class<?> columnType;

        private ProviderColumnBuilder() {
        }

        public static ProviderColumnBuilder aProviderColumn() {
            return new ProviderColumnBuilder();
        }

        public ProviderColumnBuilder withField(Field field) {
            this.field = field;
            return this;
        }

        public ProviderColumnBuilder withJavaColumn(String javaColumn) {
            this.javaColumn = javaColumn;
            return this;
        }

        public ProviderColumnBuilder withDbColumn(String dbColumn) {
            this.dbColumn = dbColumn;
            return this;
        }

        public ProviderColumnBuilder withDbInsertColumn(String dbInsertColumn) {
            this.dbInsertColumn = dbInsertColumn;
            return this;
        }

        public ProviderColumnBuilder withDbColumnTable(String dbColumnTable) {
            this.dbColumnTable = dbColumnTable;
            return this;
        }

        public ProviderColumnBuilder withIsPrimaryKey(boolean isPrimaryKey) {
            this.isPrimaryKey = isPrimaryKey;
            return this;
        }

        public ProviderColumnBuilder withColumnType(Class<?> columnType) {
            this.columnType = columnType;
            return this;
        }

        public ProviderColumn build() {
            return new ProviderColumn(field, javaColumn, dbColumn, dbColumnTable, isPrimaryKey, columnType, dbInsertColumn);
        }
    }
}
