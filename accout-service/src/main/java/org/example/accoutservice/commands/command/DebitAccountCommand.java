package org.example.accoutservice.commands.command;

import lombok.Getter;
import org.example.accoutservice.common.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class DebitAccountCommand extends BaseCommand<String> {
    private final BigDecimal amount;
    private final OperationType operationType;
    private final String description;

    public DebitAccountCommand(String id, LocalDateTime commandTime, String commandBy, BigDecimal amount, OperationType operationType, String description) {
        super(id, commandTime, commandBy);
        this.amount = amount;
        this.operationType = operationType;
        this.description = description;
    }
}
