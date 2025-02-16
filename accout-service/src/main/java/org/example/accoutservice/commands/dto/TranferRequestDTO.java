package org.example.accoutservice.commands.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TranferRequestDTO(
        @NotBlank(message = "field 'accountIdFrom' id madatory: it can not be blank")
        String accountIdFrom,
        @NotBlank(message = "field 'accountIdTo' id madatory : it can not be blank")
        String accountIdTo,
        @NotNull(message = "field 'amount' is madatory : it can not be null")
        @Positive(message = "field 'amount' must be positive")
        BigDecimal amount,
        @NotBlank(message = "field 'description' is madatory : it can not be blank")
        String description
) {
}
