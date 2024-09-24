package org.javaexercises.reactiveapiddspells.DTO;

import java.util.List;

public class SpellsResponseDto {
    private int count;
    private List<SpellDto> results;

    // Getters e Setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SpellDto> getResults() {
        return results;
    }

    public void setResults(List<SpellDto> results) {
        this.results = results;
    }
}
