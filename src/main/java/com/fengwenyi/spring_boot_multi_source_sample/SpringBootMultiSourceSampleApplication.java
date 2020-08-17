package com.fengwenyi.spring_boot_multi_source_sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengwenyi.spring_boot_multi_source_sample.entity.MedicalAdviceCategoryEntity;
import com.fengwenyi.spring_boot_multi_source_sample.entity.SyncBusinessConfigEntity;
import com.fengwenyi.spring_boot_multi_source_sample.lib.annotation.MultiDataSource;
import com.fengwenyi.spring_boot_multi_source_sample.lib.datasource.MultiDataSourceProperties;
import com.fengwenyi.spring_boot_multi_source_sample.repository.IMedicalAdviceCategoryRepository;
import com.fengwenyi.spring_boot_multi_source_sample.repository.ISyncBusinessConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@EnableJpaRepositories
@RestController
@SpringBootApplication
public class SpringBootMultiSourceSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMultiSourceSampleApplication.class, args);
    }

    @Autowired
    private MultiDataSourceProperties multiDataSourceProperties;

    @Autowired
    private ISyncBusinessConfigRepository iSyncBusinessConfigRepository;

    @Autowired
    private IMedicalAdviceCategoryRepository iMedicalAdviceCategoryRepository;

    @GetMapping("/test")
    public void test() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<SyncBusinessConfigEntity> all = iSyncBusinessConfigRepository.findAll();
        SyncBusinessConfigEntity syncBusinessConfigEntity = all.get(0);
        System.out.println(objectMapper.writeValueAsString(syncBusinessConfigEntity));

        List<MedicalAdviceCategoryEntity> all1 = iMedicalAdviceCategoryRepository.findAll();
        MedicalAdviceCategoryEntity medicalAdviceCategoryEntity = all1.get(0);
        System.out.println(objectMapper.writeValueAsString(medicalAdviceCategoryEntity));
    }
}
