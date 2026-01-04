package com.nuclea.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Nuclea API Spring Boot Application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.nuclea.api",
        "com.nuclea.api.business",
        "com.nuclea.common",
        "com.nuclea.data",
        "com.nuclea.business.caching"
})
@EntityScan(basePackages = "com.nuclea.data.entity")
@EnableJpaRepositories(basePackages = "com.nuclea.data.repository")
@ConfigurationPropertiesScan(basePackages = "com.nuclea.common.config")
public class NucleaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NucleaApiApplication.class, args);
    }
}
