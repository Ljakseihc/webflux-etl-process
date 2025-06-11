package com.epam.springboot.advanced.routers;

import com.epam.springboot.advanced.handlers.SportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SportRouter {

    @Bean
    public RouterFunction<ServerResponse> route(SportHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/sport/{sportname}", handler::createSport)
                .GET("/api/v1/sport", handler::searchSports)
                .build();
    }
}
