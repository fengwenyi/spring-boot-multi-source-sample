package com.fengwenyi.spring_boot_multi_source_sample.lib.datasource;

import com.fengwenyi.spring_boot_multi_source_sample.lib.constant.MultiDataSourceConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Erwin Feng
 * @since 2020/8/15 5:39 下午
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = MultiDataSourceConstant.MULTI_DATA_SOURCE_PREFIX)
@Getter
@Setter
public class MultiDataSourceProperties {

    public MultiDataSourceProperties() {
        log.info("开始加载多数据源配置信息<<<<<<<<<<<<<<<<<<<");
    }

    /** 多数据源 */
    private List<DataSourceProperties> multi;

}
