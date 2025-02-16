package org.example.accoutservice.commands.command;

import lombok.Getter;
import org.example.accoutservice.common.enums.AccountStatus;

import java.time.LocalDateTime;

@Getter
public class ActiveAccountCommand extends BaseCommand<String> {
    private final AccountStatus accountStatus;

    public ActiveAccountCommand(String id, AccountStatus accountStatus, LocalDateTime commandTime, String commandBy) {
        super(id, commandTime, commandBy);
        this.accountStatus = accountStatus;
    }
}
