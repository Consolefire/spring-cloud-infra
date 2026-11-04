package com.consolefire.spcli.authserver;

import com.consolefire.spcli.authserver.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("component-test")
@Import(TestContainersConfig.class)
class AuthorizationServerApplicationTests {

    @Test
    void contextLoads() {
        // Full Spring context loads successfully with Testcontainers PostgreSQL & Flyway migrations
    }
}