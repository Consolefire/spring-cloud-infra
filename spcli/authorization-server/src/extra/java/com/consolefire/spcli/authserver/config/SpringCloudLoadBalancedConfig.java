package com.consolefire.spcli.authserver.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@Configuration
class SpringCloudLoadBalancedConfig {

    // LoadBalanced RestClient.Builder for synchronous service discovery calls via gateway
    @Bean
    @LoadBalanced
    @Profile("!with-eureka-discovery")
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

}