package org.example.accoutservice.commands.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.accoutservice.common.enums.AccountStatus;

public record UpdateStatusRequestDTO(
        @NotBlank(message = "field 'accountId' is madatory : it can not be blank")
        String accountId,
        @NotNull(message = "field 'accoutStatus' is madatory : it can not be null")
        AccountStatus accountStatus
) {
}
