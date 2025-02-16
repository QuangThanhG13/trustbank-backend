package org.example.accoutservice.commands.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OperationRequestDTO(
        @NotBlank(message = "field 'accountId' id madatory: it can not be blank")
        String accountId,
        @NotNull(message = "field 'amount' is madatory: it can not be null")
        @Positive(message = "field 'amount' must be positive")
        BigDecimal amount,
        @NotBlank(message = "field 'description' is madatory: it can not be blank ")
        String description
) {
}
