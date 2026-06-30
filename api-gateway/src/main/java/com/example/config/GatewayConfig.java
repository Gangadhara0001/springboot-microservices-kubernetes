package com.example.config;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

//Configuration
public class GatewayConfig {

   /* @Bean
    RouterFunction<ServerResponse> employeeRoute(){
        return GatewayRouterFunctions.route("employee-service")
                .route("/api/employee/**",
                        request -> true,
                        BeforeFilterFunctions.uri("lb://employee-service"))
                .build();
    }
    @Bean
    RouterFunction<ServerResponse> departmentRoute(){
        return GatewayRouterFunctions.route("department-service")
                .route("/api/repartment/**",
                        request -> true,
                        BeforeFilterFunctions.uri("lb://department-service"))
                .build();
    }*/
}
