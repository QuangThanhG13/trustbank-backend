package org.example.accoutservice.common.event;

import lombok.Getter;
import org.example.accoutservice.commands.command.CreditAccountCommand;
import org.example.accoutservice.common.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class AccountCreditedEvent extends BaseEvent<String> {
    // nạp vào tài khoản.
    private final BigDecimal ammount;
    private final OperationType type;
    private final String description;

    public AccountCreditedEvent(CreditAccountCommand command) {
        super(command.getId(), command.getCommandTime(), command.getCommandBy());
        this.ammount = command.getAmount();
        this.type = command.getOperationType();
        this.description = command.getDescription();
    }

    public AccountCreditedEvent(String id, LocalDateTime eventDate, String eventBy, BigDecimal amount, OperationType type, String description) {
        super(id, eventDate, eventBy);
        this.ammount = amount;
        this.type = type;
        this.description = description;
    }
}
