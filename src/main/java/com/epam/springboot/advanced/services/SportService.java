package com.epam.springboot.advanced.services;

import com.epam.springboot.advanced.model.Sport;
import com.epam.springboot.advanced.repositories.SportRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SportService {

    @Value("${rakuten.app-id}")
    private String appId;

    @Value("${rakuten.affiliate-id}")
    private String affiliateId;

    @Value("${rakuten.app-secret}")
    private String appSecret;

    @Value("${rakuten.callback-domain}")
    private String callbackDomain;

    @Value("${rakuten.api-host}")
    private String apiHost;

    @Value("${rakuten.api-path}")
    private String apiPath;

    private final SportRepository repository;
    private final WebClient webClient;

    public SportService(SportRepository repository) {
        this.repository = repository;
        this.webClient = WebClient.builder()
                .baseUrl("https://app.rakuten.co.jp/")
                .build();
    }

    @PostConstruct
    public void loadSport() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(apiPath)
                        .queryParam("applicationId", appId)
                        .queryParam("affiliateId", affiliateId)
                        .queryParam("appSecret", appSecret)
                        .queryParam("callbackDomain", callbackDomain)
                        .queryParam("keyword", "sport")
                        .queryParam("format", "json")
                        .queryParam("genreId", "555086")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(json -> {
                    var items = json.path("Items");
                    if (!items.isArray()) {
                        return Flux.error(new RuntimeException("Unexpected response format: 'Items' is not an array"));
                    }
                    return Flux.fromIterable(items)
                            .map(item -> {
                                System.out.println(item.asText());
                                var itemNode = item.get("Item");
                                String name = itemNode.get("itemName").asText();
                                return new Sport(name);
                            });
                })
                .flatMap(sport ->
                        repository.existsByName(sport.getName())
                                .flatMap(exists -> exists ? Mono.empty() : repository.save(sport))
                )
                .subscribe();
    }
}
