package com.epam.springboot.advanced.services.impl;

import com.epam.springboot.advanced.model.Sport;
import com.epam.springboot.advanced.repositories.SportRepository;
import com.epam.springboot.advanced.services.SportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository repository;

    @Override
    public Mono<ServerResponse> createSport(ServerRequest request) {
        String sportName = request.pathVariable("sportname");
        Sport newSport = new Sport(sportName);

        return repository.existsByName(sportName)
                .flatMap(exists -> {
                    if (exists) {
                        return ServerResponse
                                .status(HttpStatus.CONFLICT)
                                .bodyValue("Sport name already exists");
                    }
                    return repository.save(newSport)
                            .flatMap(saved -> ServerResponse.ok().bodyValue(saved));
                });
    }

    @Override
    public Mono<ServerResponse> searchSports(ServerRequest request) {
        String query = request.queryParam("q").orElse("");
        return ServerResponse.ok().body(repository.findByNameContainingIgnoreCase(query), Sport.class);
    }
}
