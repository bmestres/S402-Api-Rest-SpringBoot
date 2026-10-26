package cat.itacademy.s04.t02.n01.fruitapimysql.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FruitResponseDto(
        Long id,
        String name,
        @JsonProperty("weight_in_kilos")
        int weightInKilos
) { }
