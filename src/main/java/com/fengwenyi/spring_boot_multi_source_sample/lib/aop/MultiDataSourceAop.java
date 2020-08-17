package com.fengwenyi.spring_boot_multi_source_sample.lib.aop;

import com.fengwenyi.spring_boot_multi_source_sample.lib.datasource.lookup.DynamicDataSource;
import com.fengwenyi.spring_boot_multi_source_sample.lib.annotation.MultiDataSource;
import com.fengwenyi.spring_boot_multi_source_sample.lib.constant.MultiDataSourceConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Erwin Feng
 * @since 2020/8/15 5:46 下午
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(prefix = MultiDataSourceConstant.MULTI_DATA_SOURCE_PREFIX,
        value = MultiDataSourceConstant.MULTI_ENABLE, havingValue = "true", matchIfMissing = false)
public class MultiDataSourceAop implements Ordered {

    /**
     * aop的顺序要早于spring的事务
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }

    public MultiDataSourceAop() {
        log.info("多数据源初始化 AOP ");
    }

    @Pointcut(value = "@annotation(com.fengwenyi.spring_boot_multi_source_sample.lib.annotation.MultiDataSource)")
    private void cut() {
    }

    @Around("cut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Signature signature = point.getSignature();
        MethodSignature methodSignature ;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        //获取当点方法的注解
        Object target = point.getTarget();

        Class<?> clazz = target.getClass();
        MultiDataSource clazzMulti = clazz.getAnnotation(MultiDataSource.class);
        setDataSource(clazzMulti);

        if (clazzMulti == null) {
            // 如果类或者接口上没有多数据源注解，就判断方法上有没有
            Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            MultiDataSource methodMulti = currentMethod.getAnnotation(MultiDataSource.class);
            setDataSource(methodMulti);
        }

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息！");
            DynamicDataSource.clearDataSourceDbName();
        }
    }

    private void setDataSource(MultiDataSource multiDataSource) {
        if (multiDataSource != null) {
            DynamicDataSource.setDataSourceDbName(multiDataSource.value());
            log.debug("设置数据源为：" + multiDataSource.value());
        } else {
            DynamicDataSource.setDataSourceDbName(MultiDataSourceConstant.MAIN_NAME);
            log.debug("设置数据源为：默认  -->  " + MultiDataSourceConstant.MAIN_NAME);
        }
    }
}
