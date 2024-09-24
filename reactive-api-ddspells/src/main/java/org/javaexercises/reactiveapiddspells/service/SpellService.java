package org.javaexercises.reactiveapiddspells.service;

import org.javaexercises.reactiveapiddspells.DTO.SpellsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SpellService {

    private final WebClient webClient;

    @Autowired
    public SpellService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<SpellsResponseDto> getSpellByName(String spellName) {
        String url = String.format("?name=%s", spellName);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(SpellsResponseDto.class);
    }
}
