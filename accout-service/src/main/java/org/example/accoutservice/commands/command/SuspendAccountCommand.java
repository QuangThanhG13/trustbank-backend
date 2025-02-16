package org.example.accoutservice.commands.command;


import lombok.Getter;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.event.AccountSuspendedEvent;

import java.time.LocalDateTime;

@Getter
public class SuspendAccountCommand extends BaseCommand<String> {
    private final AccountStatus status;
    public SuspendAccountCommand(String id, LocalDateTime localDateTime, String commandBy, AccountStatus status) {
        super(id, localDateTime, commandBy);
        this.status = status;
    }
}
