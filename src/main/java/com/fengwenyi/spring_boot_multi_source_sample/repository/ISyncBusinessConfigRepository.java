package com.fengwenyi.spring_boot_multi_source_sample.repository;

import com.fengwenyi.spring_boot_multi_source_sample.entity.SyncBusinessConfigEntity;
import com.fengwenyi.spring_boot_multi_source_sample.lib.annotation.MultiDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Erwin Feng
 * @since 2020/8/17 3:36 下午
 */
@MultiDataSource("uniform")
@Repository
public interface ISyncBusinessConfigRepository extends JpaRepository<SyncBusinessConfigEntity, String> {
}
