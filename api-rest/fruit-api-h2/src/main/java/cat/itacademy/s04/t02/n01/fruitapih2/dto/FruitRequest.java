package cat.itacademy.s04.t02.n01.fruitapih2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record FruitRequest(
        @NotBlank(message = "Name must not be empty")
        String name,
        @Min(value = 1, message = "Weight must be greater than 0")
        @JsonProperty("weight_in_kilos")
        int weightInKilos
) { }
