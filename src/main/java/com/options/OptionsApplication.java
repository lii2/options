package com.options;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({"com.options.controller", "com.options.operations"})
@EnableJpaRepositories(basePackages = "com.options.repositories")
@EntityScan(basePackages = "com.options.entities")
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class OptionsApplication {
    // possibly use russel as lead
    public static void main(String[] args) {
        SpringApplication.run(OptionsApplication.class, args);
    }
}
