package com.example.config;

import com.example.service.EmployeeService;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetryConfig {
    @Bean
    public RetryRegistry retryRegistry(){
        return RetryRegistry.ofDefaults();
    }

    @Bean
    public ApplicationRunner retryEvents(RetryRegistry retryRegistry){
        return args -> {
            retryRegistry.retry("departmentRetry")
                    .getEventPublisher()
                    .onRetry(event ->
                            System.out.println(
                                    "Retry Attempt: "
                                    +event.getNumberOfRetryAttempts()
                            ));
        };
    }
    @Bean
    public ApplicationRunner testRunner(EmployeeService employeeService) {
        return args -> {
            System.out.println("EmployeeService Class = "
                    + employeeService.getClass());
        };
    }
}
