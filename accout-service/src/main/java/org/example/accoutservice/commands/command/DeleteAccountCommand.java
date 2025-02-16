package org.example.accoutservice.commands.command;

import lombok.Getter;

import java.time.LocalDateTime;


public class DeleteAccountCommand extends BaseCommand<String> {
    public DeleteAccountCommand(String id, LocalDateTime commandTime, String commandBy) {
        super(id, commandTime, commandBy);
    }
}
