package org.example.accoutservice.queries.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.accoutservice.common.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationResponseDTO {
    private String id;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private OperationType type;
    private String description;
    private String createdBy;
    private String accountId;
}
