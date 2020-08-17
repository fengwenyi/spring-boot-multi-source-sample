package com.fengwenyi.spring_boot_multi_source_sample.repository;

import com.fengwenyi.spring_boot_multi_source_sample.entity.MedicalAdviceCategoryEntity;
import com.fengwenyi.spring_boot_multi_source_sample.lib.annotation.MultiDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Erwin Feng
 * @since 2020/8/17 3:42 下午
 */
@MultiDataSource("hosplat_medical_service")
@Repository
public interface IMedicalAdviceCategoryRepository extends JpaRepository<MedicalAdviceCategoryEntity, Integer> {
}
