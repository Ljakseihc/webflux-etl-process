package com.epam.springboot.advanced.services;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface SportService {
    Mono<ServerResponse> createSport(ServerRequest request);
    Mono<ServerResponse> searchSports(ServerRequest request);
}
