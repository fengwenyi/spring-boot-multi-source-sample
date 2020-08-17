package com.fengwenyi.spring_boot_multi_source_sample.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Erwin Feng
 * @since 2020/8/17 3:40 下午
 */
@Data
@Entity
@Table(name = "cc_medical_advice_category")
public class MedicalAdviceCategoryEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

}
