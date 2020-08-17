package com.fengwenyi.spring_boot_multi_source_sample.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Erwin Feng
 * @since 2020/8/17 3:30 下午
 */
@Data
@Entity
@Table(name = "sync_business_config")
public class SyncBusinessConfigEntity {

    @Id
    @Column(name = "_X_ID")
    private String xId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

}
