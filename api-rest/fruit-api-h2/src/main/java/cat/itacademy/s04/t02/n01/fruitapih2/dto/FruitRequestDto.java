package cat.itacademy.s04.t02.n01.fruitapih2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FruitRequestDto(
        String name,
        @JsonProperty("weight_in_kilos")
        int weightInKilos
) { }
