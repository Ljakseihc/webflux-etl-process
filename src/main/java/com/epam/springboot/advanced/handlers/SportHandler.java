package com.epam.springboot.advanced.handlers;

import com.epam.springboot.advanced.model.Sport;
import com.epam.springboot.advanced.repositories.SportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SportHandler {

    private final SportRepository sportRepository;

    public SportHandler(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }


}
