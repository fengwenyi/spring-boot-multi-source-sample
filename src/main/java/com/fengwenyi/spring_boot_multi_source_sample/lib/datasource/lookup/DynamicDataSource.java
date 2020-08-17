package com.fengwenyi.spring_boot_multi_source_sample.lib.datasource.lookup;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源配置
 * 动态数据源
 * @author Erwin Feng
 * @since 2020/8/17 10:07 上午
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * <p>多 datasource 的上下文</p>
     * <p>每个线程独立的数据库连接名称</p>
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();


    /**
     * @param dataSourceDbName 数据库别名
     * @Description: 设置数据源别名
     */
    public static void setDataSourceDbName(String dataSourceDbName) {
        contextHolder.set(dataSourceDbName);
    }

    /**
     * @Description: 获取数据源别名
     */
    public static String getDataSourceDbName() {
        return contextHolder.get();
    }

    /**
     * @Description: 清除数据源别名
     */
    public static void clearDataSourceDbName() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSourceDbName();
    }
}
