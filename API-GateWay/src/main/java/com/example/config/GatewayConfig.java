package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

            // CUSTOMER-SERVICE: accessible by USER or ADMIN
            .route("customer-service", r -> r.path("/api/customer/**")
                .filters(f -> {
                    JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                    return f.filter(jwtAuthenticationFilter.apply(config));
                })
                .uri("lb://CUSTOMER-SERVICE"))

            // ACCOUNT-SERVICE: accessible by USER or ADMIN
            .route("account-service", r -> r.path("/api/account/**")
                .filters(f -> {
                    JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                    return f.filter(jwtAuthenticationFilter.apply(config));
                })
                .uri("lb://ACCOUNT-SERVICE"))

            // PAYMENT-SERVICE: accessible only by USER (who performs payment)
            .route("payment-service", r -> r.path("/api/payment/**")
                .filters(f -> {
                    JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                    config.setRequiredRole("USER");
                    return f.filter(jwtAuthenticationFilter.apply(config));
                })
                .uri("lb://PAYMENT-SERVICE"))

            // NOTIFICATION-SERVICE: internal calls only (secured by IP or service-to-service comms, no auth here)
            .route("notification-service", r -> r.path("/api/notifications/**")
                .uri("lb://NOTIFICATION-SERVICE"))

            // AUDIT-SERVICE: accessible only by ADMIN (to check logs)
            .route("audit-service", r -> r.path("/api/audit/**")
                .filters(f -> {
                    JwtAuthenticationFilter.Config config = new JwtAuthenticationFilter.Config();
                    config.setRequiredRole("ADMIN");
                    return f.filter(jwtAuthenticationFilter.apply(config));
                })
                .uri("lb://AUDIT-SERVICE"))

            .build();
    }
}
