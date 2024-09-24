package org.javaexercises.reactiveapiddspells.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) throws Exception {

        return builder
                .baseUrl("https://www.dnd5eapi.co/api/spells/")
                .build();
    }
}
