package cat.itacademy.s04.t02.n01.fruitapimysql.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FruitRequestDto(
        @NotNull(message = "Name must not be NULL")
        @NotBlank(message = "Name must not be empty")
        String name,

        @Positive(message = "Weight must be greater than 0")
        @JsonProperty("weight_in_kilos")
        int weightInKilos,

        @NotNull(message = "Supplier ID must not be NULL")
        @JsonProperty("supplier_id")
        Long supplierId
) { }
