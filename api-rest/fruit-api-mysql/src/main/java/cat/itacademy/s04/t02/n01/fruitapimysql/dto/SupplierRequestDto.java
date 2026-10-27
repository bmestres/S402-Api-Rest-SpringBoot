package cat.itacademy.s04.t02.n01.fruitapimysql.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SupplierRequestDto(
        @NotNull(message = "Name must not be NULL")
        @NotBlank(message = "Name must not be empty")
        String name,
        @NotNull(message = "Country must not be NULL")
        @NotBlank(message = "Country must not be empty")
        String country
) {
}
