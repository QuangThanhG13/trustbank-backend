package org.example.accoutservice.commands.command;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Getter
public class BaseCommand<T> {
    @TargetAggregateIdentifier
    private final T id;
    private final LocalDateTime commandTime;
    private final String commandBy;

    public BaseCommand(T id, LocalDateTime commandTime, String commandBy) {
        this.id = id;
        this.commandTime = commandTime;
        this.commandBy = commandBy;
    }

}
