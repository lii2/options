package com.options;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@ComponentScan({"com.options.controller", "com.options.agents", "com.options.clients.database", "com.options.clients.alphavantage"})
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

    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowserAfterStartup() throws IOException {
        System.out.println("Launching Browser");

        Runtime rt = Runtime.getRuntime();
        String url = "http://localhost:8081/";
        rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
    }
}
