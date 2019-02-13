package be.kdg.spring_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SpringGatewayApplication {

    @Bean
    public RouteLocator myRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("hello", r -> r.path("/hello")
                            .uri("http://localhost:8081"))
                .route("student", r -> r.path("/studentservice/**")
                            .filters(
                                    f -> f.stripPrefix(1)
                            )
                            .uri("lb://student-microservice"))
                .route("school", r -> r.path("/schoolservice/**")
                            .filters(
                                    f -> f.stripPrefix(1)
                            )
                            .uri("lb://school-microservice"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringGatewayApplication.class, args);
    }

}

