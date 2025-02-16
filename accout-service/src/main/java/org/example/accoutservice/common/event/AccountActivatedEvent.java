package org.example.accoutservice.common.event;

import lombok.Getter;
import org.example.accoutservice.commands.command.ActiveAccountCommand;
import org.example.accoutservice.common.enums.AccountStatus;

import java.time.LocalDateTime;

@Getter
public class AccountActivatedEvent extends BaseEvent<String> {
    private final AccountStatus status;

    public AccountActivatedEvent(ActiveAccountCommand command) {
        super(command.getId(), command.getCommandTime(), command.getCommandBy());
        this.status = command.getAccountStatus();
    }

    public AccountActivatedEvent(String id, LocalDateTime eventDate, String eventBy, AccountStatus status) {
        super(id, eventDate, eventBy);
        this.status = status;
    }
}
