package com.consolefire.spcli.authserver;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.consolefire.spcli.authserver"})
public class AuthorizationServerApplication {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

    @PostConstruct
    void print() {
        log.info("dataSourceUrl: {}", dataSourceUrl);
    }
}
