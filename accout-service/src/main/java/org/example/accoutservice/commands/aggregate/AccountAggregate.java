package org.example.accoutservice.commands.aggregate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.accoutservice.commands.command.*;
import org.example.accoutservice.commands.exception.AccountNotActivatedException;
import org.example.accoutservice.commands.exception.AmountNotSufficientException;
import org.example.accoutservice.commands.exception.BalanceNotSufficientException;
import org.example.accoutservice.commands.exception.NotAuthorizedException;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.enums.Currency;
import org.example.accoutservice.common.event.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Aggregate
@Getter
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private AccountStatus status;
    private BigDecimal balance; //số dư
    private Currency currency; //mệnh giá tiền USD , VND
    private String customerId;
    private String email;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String lastModifiedBy; //lưu trữ thông tin về người hoặc hệ thống đã cập nhật lần cuối tài khoản

    public AccountAggregate() {
        super();
    }

    @CommandHandler
    public AccountAggregate (CreateAccountCommand command) {
        log.info("CreateAccountCommand handle start");
        AccountCreatedEvent event = new AccountCreatedEvent(command.getId(), LocalDateTime.now(), command.getCommandBy(), command.getAccountStatus(), command.getBalance(), command.getCurrency(), command.getCustomerId(), command.getEmail());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(@NotNull AccountCreatedEvent event) {
        log.info("AccountCreditedEvent handle start");
        this.accountId = event.getId();
        this.status = event.getStatus();
        this.balance = event.getBalance();
        this.currency = event.getCurrency();
        this.customerId = event.getCustomerId();
        this.email = event.getEmail();
        this.createdBy = event.getEnventBy();
        this.createDate = event.getEventDate();

        AccountActivatedEvent accountActivatedEvent = new AccountActivatedEvent(this.accountId, this.createDate, this.createdBy, AccountStatus.ACTIVATED);
        AggregateLifecycle.apply(accountActivatedEvent);
    }

    @CommandHandler
    public void handle(ActiveAccountCommand command) {
        log.info("AccountActivatedEvent command");
        AccountActivatedEvent event = new AccountActivatedEvent(command);
        AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        log.info("AccountActivatedEvent handle start");
        this.accountId = event.getId();
        this.lastModifiedBy = event.getEnventBy();
        this.modifiedDate = event.getEventDate();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(SuspendAccountCommand command) {
        log.info("SuspendAccountCommand handle start");
        AccountSuspendedEvent event = new AccountSuspendedEvent(command);
    }

    @EventSourcingHandler
    public void on(@NotNull AccountSuspendedEvent event) {
        log.info("AccountSuspendedEvent handle start");
        this.accountId = event.getId();
        this.lastModifiedBy = event.getEnventBy();
        this.modifiedDate = event.getEventDate();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(@NotNull CreditAccountCommand command) {
        log.info("CreditAccountCommand handle start");
        if (command.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new AmountNotSufficientException(" amount not sufficient  " + command.getAmount());
        } else if (!this.status.equals(AccountStatus.ACTIVATED)) {
            throw new AccountNotActivatedException("Account not activated" + this.status);
        } else { //nếu số tiền cần ghi có giá trị lớn hơn 0 và tài khoản đang hoạt động
            AccountCreditedEvent event = new AccountCreditedEvent(command);
            AggregateLifecycle.apply(event);
        }
    }

    @EventSourcingHandler
    public void on(@NotNull AccountCreditedEvent event) {
        log.info("AccountCreatedEvent handle start");
        this.accountId = event.getId();
        this.lastModifiedBy = event.getEnventBy();
        this.modifiedDate = event.getEventDate();
        this.balance = this.balance.add(event.getAmmount());
    }

    @CommandHandler
    public void handle(DebitAccountCommand command) {
        log.info("DebitAccountCommand handle start");
        if (command.getAmount().compareTo(BigDecimal.ZERO) < 0 || command.getAmount().compareTo(this.balance) > 0) {
            throw new BalanceNotSufficientException("Balance not sufficient " + command.getAmount());
        } else if (!this.status.equals(AccountStatus.ACTIVATED)) {
            throw new AccountNotActivatedException("Account not activated" + this.status);
        } else {
            AccountDebitedEvent event = new AccountDebitedEvent(command);
            AggregateLifecycle.apply(event);
        }
    }

    @EventSourcingHandler
    public void on(@NotNull AccountDebitedEvent event) {
        log.info("AccountDebitedEvent handle start");
        this.accountId = event.getId();
        this.lastModifiedBy = event.getEnventBy();
        this.modifiedDate = event.getEventDate();
        this.balance = this.balance.subtract(event.getAmount());
    }

    @CommandHandler
    public void handle(@NotNull DeleteAccountCommand command) {
        log.info("DebitAccountCommand handle start");
        if (this.balance.compareTo(BigDecimal.ZERO) > 0) {
            throw new NotAuthorizedException("Not authorized : The balance must be 0 in order to delete the account.=> " + this.balance);
        } else {
            AccountDeletedEvent event = new AccountDeletedEvent(command);
            AggregateLifecycle.apply(event);
        }
    }

    @EventSourcingHandler
    public void on(@NotNull AccountDeletedEvent event) {
        log.info("AccountDeletedEvent handle start");
        this.accountId = event.getId();
        this.lastModifiedBy = event.getEnventBy();
        this.modifiedDate = event.getEventDate();
        this.status = AccountStatus.DELETED;
        this.email = null;
        this.customerId = null;
    }
}
