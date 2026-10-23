package cat.itacademy.s04.t02.n01.fruitapih2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FruitRequest(
        @NotBlank(message = "Name must not be empty")
        String name,
        @Positive(message = "Weight must be greater than 0")
        @JsonProperty("weight_in_kilos")
        int weightInKilos
) { }
