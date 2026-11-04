package com.consolefire.spcli.user;

import com.consolefire.spcli.user.config.TestContainersConfig;
import com.consolefire.spcli.user.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("component-test")
@Import({TestContainersConfig.class, TestSecurityConfig.class})
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
