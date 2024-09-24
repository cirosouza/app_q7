package org.javaexercises.reactiveapiddspells.controller;

import org.javaexercises.reactiveapiddspells.DTO.SpellsResponseDto;
import org.javaexercises.reactiveapiddspells.service.SpellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SpellController {

    private final SpellService spellService;

    @Autowired
    public SpellController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping("/spells/{name}")
    public Mono<SpellsResponseDto> getSpell(@PathVariable String name) {
        return spellService.getSpellByName(name);
    }
}
