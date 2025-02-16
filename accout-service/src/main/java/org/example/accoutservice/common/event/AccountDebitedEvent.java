package org.example.accoutservice.common.event;

import lombok.Getter;
import org.example.accoutservice.commands.command.DebitAccountCommand;
import org.example.accoutservice.common.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AccountDebitedEvent extends BaseEvent<String> {
    //rút tiền khỏi tk
    private final BigDecimal amount;
    private final OperationType type;
    private final String description;

    public AccountDebitedEvent(String id, LocalDateTime evenDate, String evenBy, BigDecimal amount, OperationType type, String description) {
        super(id ,evenDate , evenBy);
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public AccountDebitedEvent(DebitAccountCommand command) {
        super(command.getId(), command.getCommandTime(), command.getCommandBy());
        this.amount = command.getAmount();
        this.type = command.getOperationType();
        this.description = command.getDescription();
    }
}
