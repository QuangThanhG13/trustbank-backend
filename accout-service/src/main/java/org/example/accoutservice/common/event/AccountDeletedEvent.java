package org.example.accoutservice.common.event;


import org.example.accoutservice.commands.command.DeleteAccountCommand;

import java.time.LocalDateTime;

public class AccountDeletedEvent extends BaseEvent<String>{
    public AccountDeletedEvent(String id, LocalDateTime eventDate, String eventBy) {
        super(id, eventDate, eventBy);
    }

    public AccountDeletedEvent(DeleteAccountCommand command) {
        super(command.getId(), command.getCommandTime(), command.getCommandBy());
    }
}
