package com.nuclea.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Scheduler Application (Hangfire equivalent with Spring Scheduler/Quartz).
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {
        "com.nuclea.scheduler",
        "com.nuclea.scheduler.business",
        "com.nuclea.common",
        "com.nuclea.data"
})
@EntityScan(basePackages = "com.nuclea.data.entity")
@EnableJpaRepositories(basePackages = "com.nuclea.data.repository")
public class SchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }
}
