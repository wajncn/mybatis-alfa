package com.github.wz2cool.alfa.helper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Frank
 * @author wangjin
 */
public class CommonsHelper {
    private static final String SYMBOL = "%s";
    private static final int SYMBOL_LENGTH = SYMBOL.length();

    private static ConcurrentHashMap<String, Class> classMap = new ConcurrentHashMap<>();

    private CommonsHelper() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNumeric(final Class targetClass) {
        if (targetClass == null) {
            return false;
        }
        return Number.class.isAssignableFrom(targetClass);
    }

    public static boolean isArrayOrCollection(final Object value) {
        return isArray(value) || isCollection(value);
    }

    public static boolean isArray(final Object value) {
        return value != null && value.getClass().isArray();
    }

    public static boolean isCollection(final Object value) {
        return value instanceof Iterable;
    }

    public static Object[] getCollectionValues(final Object inputValue) {
        if (inputValue == null) {
            throw new NullPointerException("inputValue");
        }

        if (!isArrayOrCollection(inputValue)) {
            throw new IllegalArgumentException("inputValue should be array or collection");
        }
        Collection<Object> values = new ArrayList<>();
        if (inputValue instanceof Iterable) {
            Iterable iterable = (Iterable) inputValue;
            for (Object value : iterable) {
                values.add(value);
            }
        } else {
            int length = Array.getLength(inputValue);
            for (int i = 0; i < length; i++) {
                Object value = Array.get(inputValue, i);
                values.add(value);
            }
        }

        return values.toArray();
    }

    public static String toStringSafe(final Object obj) {
        if (obj == null) {
            return "";
        }

        return obj.toString();
    }

    /**
     * 字符串替换,表示符号为: {@link CommonsHelper#SYMBOL}
     * <code>
     * format("%s,%s", "A", "B");           AB
     * format("%s,%s", "A", "B", "C");      A,B
     * format("%s,%s,%s", "A");             A,%s,%s
     * format("%s,%s,%s", "A", "B", "C");   A,B,C
     * </code>
     *
     * @param pattern   根据SYMBOL去替换arguments
     * @param arguments SYMBOL的替换值
     * @return String
     */
    public static String format(String pattern, String... arguments) {
        final StringBuilder stringBuilder = new StringBuilder(pattern.length() + 16);
        //当前SYMBOL的坐标
        int indexOf = -1;
        //从什么位置开始找SYMBOL坐标
        int fromIndex = 0;
        for (String argument : arguments) {
            indexOf = pattern.indexOf(SYMBOL, fromIndex);
            if (indexOf == -1) {
                if (fromIndex == 0) {
                    //直接返回,没有匹配到
                    return pattern;
                } else {
                    //这里说明SYMBOL加多了. 那就容错处理.添加后面所有的字符串返回
                    return stringBuilder.append(pattern, fromIndex, pattern.length()).toString();
                }
            }
            stringBuilder.append(pattern, fromIndex, indexOf);
            stringBuilder.append(argument);
            //当前坐标加上SYMBOL_LENGTH,继续循环判断
            fromIndex = indexOf + SYMBOL_LENGTH;
        }
        stringBuilder.append(pattern, fromIndex, pattern.length());
        return stringBuilder.toString();
    }
}