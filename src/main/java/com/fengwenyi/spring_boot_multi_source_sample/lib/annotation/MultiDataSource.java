package com.fengwenyi.spring_boot_multi_source_sample.lib.annotation;

import com.fengwenyi.spring_boot_multi_source_sample.lib.constant.MultiDataSourceConstant;

import java.lang.annotation.*;

/**
 * @author Erwin Feng
 * @since 2020/8/15 5:19 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface MultiDataSource {

    String value() default MultiDataSourceConstant.MAIN_NAME;

}
