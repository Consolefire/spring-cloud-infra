package com.consolefire.spcli.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ConfigServerApplicationTests {

    @Test
    void contextLoads() {
        // Config Server context loads successfully using the native test profile
    }

}