package org.example.accoutservice.commands.dto;

import jakarta.validation.constraints.NotBlank;
import org.example.accoutservice.common.enums.Currency;

public record AccountRequestDTO (
        @NotBlank(message =  "field 'customerId' is mandatory : it can not be null")
        String customerId ,
        @NotBlank(message="field 'currency' is mandatory: it can not be null")
        Currency currency) {
}


