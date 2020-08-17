package com.fengwenyi.spring_boot_multi_source_sample.lib.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * @author Erwin Feng
 * @since 2020/8/15 5:22 下午
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
@Setter
public class DataSourceProperties {

    public DataSourceProperties() {
        log.info("开始加载数据源配置信息<<<<<<<<<<<<<<");
    }

    /** 名称，多数据源，取一个名称 */
    private String name;

    /** url */
    private String url;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 驱动，默认为 MySQL 8.x  */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    private Map<String, String> properties;

//    private String dialect = "org.hibernate.dialect.MySQL5Dialect";

    // 使用默认的配置
    public DataSource defaultConfig() {
        return DataSourceBuilder.create().build();
    }

    public HikariDataSource config() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        Properties prop = new Properties();
        properties.forEach(prop::setProperty);

        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        hikariConfig.setDataSourceProperties(prop);

        return new HikariDataSource(hikariConfig);
    }

    @Override
    public String toString() {
        return "DataProperties{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                '}';
    }
}
