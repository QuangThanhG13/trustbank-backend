package org.example.accoutservice.commands.command;

import lombok.Getter;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CreateAccountCommand extends BaseCommand<String> {
    private final AccountStatus accountStatus;
    private final BigDecimal balance;
    private final Currency currency;
    private final String customerId;
    private final String email;

    public CreateAccountCommand(String id, LocalDateTime commandDate, AccountStatus accountStatus, BigDecimal balance, Currency currency, String customerId, String email, String commandBy) {
        super(id, commandDate, commandBy);
        this.accountStatus = accountStatus;
        this.balance = balance;
        this.currency = currency;
        this.customerId = customerId;
        this.email = email;
    }
}
