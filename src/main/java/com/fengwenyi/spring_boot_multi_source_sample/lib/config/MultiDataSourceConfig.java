package com.fengwenyi.spring_boot_multi_source_sample.lib.config;

import com.fengwenyi.spring_boot_multi_source_sample.lib.datasource.lookup.DynamicDataSource;
import com.fengwenyi.spring_boot_multi_source_sample.lib.constant.MultiDataSourceConstant;
import com.fengwenyi.spring_boot_multi_source_sample.lib.datasource.DataSourceProperties;
import com.fengwenyi.spring_boot_multi_source_sample.lib.datasource.MultiDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 多数据源配置
 * @author Erwin Feng
 * @since 2020/8/17 10:12 上午
 */
@Slf4j
@Configuration
public class MultiDataSourceConfig {

    @Autowired
    private DataSourceProperties dataProperties;

    @Autowired
    private MultiDataSourceProperties multiDataSourceConfig;

    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = MultiDataSourceConstant.MULTI_DATA_SOURCE_PREFIX,
            value = MultiDataSourceConstant.MULTI_ENABLE, havingValue = "false", matchIfMissing = true)
    public DataSource singleDatasource() {
        log.error("singleDatasource");
        return dataProperties.defaultConfig();
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = MultiDataSourceConstant.MULTI_DATA_SOURCE_PREFIX,
            value = MultiDataSourceConstant.MULTI_ENABLE, havingValue = "true", matchIfMissing = false)
    public DynamicDataSource multiDataSource() {
        log.info("multiDataSource");

        //存储数据源别名与数据源的映射
        HashMap<Object, Object> dbNameMap = new HashMap<>();

        /*List<DataProperties> multi = multiDataSourceConfig.getMulti();


        // 核心数据源
        DataSource mainDataSource = dataProperties.config();

        // 测试默认数据源是否正确

        // 这里添加 主要数据库，其它数据库挂了，默认使用主数据库
        dbNameMap.put("main", mainDataSource);*/


        // 其它数据源
        // 当前多数据源是否存在
        if (multiDataSourceConfig.getMulti() != null) {
            List<HikariDataSource> multiDataSourceList = multiDataSourceConfig.getMulti().stream()
                    .filter(dp -> !"".equals(Optional.ofNullable(dp.getName()).orElse("")))
                    .map(dp -> {
                        HikariDataSource hikariDataSource = dp.config();
                        dbNameMap.put(dp.getName(), hikariDataSource);
                        return hikariDataSource;
                    })
                    .collect(Collectors.toList());

            // 测试数据源是否正确

        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dbNameMap);
//        dynamicDataSource.setDefaultTargetDataSource(mainDataSource);
        return dynamicDataSource;
    }

}
