package org.example.accoutservice.queries.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountResponseDTO {
    private String id;
    private AccountStatus status;
    private BigDecimal balance;
    private Currency currency;
    private String customerId;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
}
