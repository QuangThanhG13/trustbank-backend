package org.example.accoutservice.queries.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.enums.OperationType;
import org.example.accoutservice.common.event.*;
import org.example.accoutservice.queries.entity.Account;
import org.example.accoutservice.queries.entity.Operation;
import org.example.accoutservice.queries.exception.AccountNotFoundException;
import org.example.accoutservice.queries.respository.AccountRepository;
import org.example.accoutservice.queries.respository.OperationRepository;
import org.example.accoutservice.queries.until.notification.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
public class AccountEventHandlerService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final NotificationService notificationService;

    public AccountEventHandlerService(AccountRepository accountRespository, OperationRepository operationRespository, NotificationService notificationService) {
        this.accountRepository = accountRespository;
        this.operationRepository = operationRespository;
        this.notificationService = notificationService;
    }

    @EventHandler
    public Account handleAccountCreatedEvent(@NotNull AccountCreatedEvent event) {
        log.info("AccountCreatedEvent received");
        Account account = buildNewAccount(event);
        Account saveAccount = accountRepository.save(account);
        log.info("Account with id {} by {} at {}", saveAccount.getId() , saveAccount.getCreatedBy(), saveAccount.getCreatedDate());
        notificationService.sendAccountCreationNotification(event.getId(), event.getEmail(), event.getEventDate());
        return saveAccount;
    }

    @EventHandler
    public Account handleAccountActivatedEvent(@NotNull AccountActivatedEvent event) {
        log.info("AccountActivatedEvent received");
        Account account = findAccountById(event.getId());
        updateAccountStatus(account, event.getStatus(), event.getEnventBy(), event.getEventDate());
        Account activatedAccount = accountRepository.save(account);
        log.info("Account with id {} activated by {} at {}", activatedAccount.getId(), activatedAccount.getLastModifiedBy(), activatedAccount.getLastModifiedDate());
        return activatedAccount;
    }

    @EventHandler
    public Account handleAccountSuspendedEvent(@NotNull AccountSuspendedEvent event) {
        log.info("AccountSuspendedEvent received");
        Account account = findAccountById(event.getId());
        updateAccountStatus(account, event.getStatus(), event.getEnventBy(), event.getEventDate());
        Account suspendedAccount = accountRepository.save(account);
        log.info("Account with id {} suspended by {} at {}", suspendedAccount.getId(), suspendedAccount.getLastModifiedBy(), suspendedAccount.getLastModifiedDate());
        return suspendedAccount;
    }

    @EventHandler
    public void handleAccountDeleteEvent(@NotNull AccountDeletedEvent event) {
        log.info("AccountDeletedEvent received");
        Account account = findAccountById(event.getId());
        operationRepository.deleteByAccountId(account.getId());
        log.info("All operations linked to account with id {} deleted", event.getId());
        accountRepository.deleteById(account.getId());
        log.info("Account with id {} deleted by {} at {}", account.getId(), account.getLastModifiedBy(), account.getLastModifiedDate());
        notificationService.sendAccountDeleteNotification(event.getId(), account.getEmail(), event.getEventDate());
    }

    @EventHandler
    public Operation handleAccountCreditedEvent(@NotNull AccountCreditedEvent event) {
        log.info("AccountCreditedEvent received");
        Account account = findAccountById(event.getId());
        updateAccountBalance(account, event.getAmmount(), event.getEnventBy(), event.getEventDate(), true);
        Account creditedAccount = accountRepository.save(account);
        log.info("Account with id '{}' credited with amount '{}' at '{}' by {}", creditedAccount.getId(), event.getAmmount(), creditedAccount.getLastModifiedDate(), creditedAccount.getLastModifiedBy());
        Operation operation = createOperation(creditedAccount , event.getAmmount(), event.getType(), event.getDescription(), event.getEnventBy(), event.getEventDate());
        Operation creditOperation = operationRepository.save(operation);
        log.info("Credit Operation with id '{}'", creditOperation.getId());
        notificationService.sendAccountCreditedNotification(creditedAccount.getEmail(), event.getAmmount(), creditedAccount.getBalance(), event.getEventDate());
        return creditOperation;
    }

    @EventHandler
    public Operation handleAccountDebitedEvent(@NotNull AccountDebitedEvent event){
        log.info("AccountDebitedEvent received");
        Account account = findAccountById(event.getId());
        updateAccountBalance(account, event.getAmount(), event.getEnventBy(), event.getEventDate(), false);
        Account debitedAccount = accountRepository.save(account);
        log.info("Account with id '{}' debited with amount '{}' at '{}' by {}", debitedAccount.getId(), event.getAmount(), debitedAccount.getLastModifiedDate(), debitedAccount.getLastModifiedBy());
        Operation operation = createOperation(debitedAccount, event.getAmount(), event.getType(), event.getDescription(), event.getEnventBy(), event.getEventDate());
        Operation debitOperation = operationRepository.save(operation);
        log.info("Debit Operation saved with id '{}'", debitOperation.getId());
        notificationService.sendAccountDebitedNotification(debitedAccount.getEmail(), event.getAmount(), debitedAccount.getBalance(), event.getEventDate());
        return debitOperation;
    }

    private Account buildNewAccount(@NotNull AccountCreatedEvent event) {
        return Account.builder()
                .id(event.getId())
                .email(event.getEmail())
                .customerId(event.getCustomerId())
                .status(event.getStatus())
                .balance(event.getBalance())
                .createdBy(event.getEnventBy())
                .createdDate(event.getEventDate())
                .build();
    }

    private void updateAccountStatus(@NotNull Account account, AccountStatus status,
                                     String modifiedBy, LocalDateTime modifiedDate) {
        account.setStatus(status);
        account.setLastModifiedBy(modifiedBy);
        account.setLastModifiedDate(modifiedDate);
    }

    private void updateAccountBalance(@NotNull Account account, BigDecimal amount ,
                                      String modifiedBy, LocalDateTime modifiedDate, boolean isCredit) {
        account.setLastModifiedBy(modifiedBy);
        account.setLastModifiedDate(modifiedDate);
        account.setBalance(isCredit ? account.getBalance().add(amount) : account.getBalance().subtract(amount));
    }

    private Operation createOperation(@NotNull Account account, BigDecimal amount,
                                      OperationType type, String description, String createdBy,
                                      LocalDateTime dateTime) {
        return Operation.builder()
                .account(account)
                .amount(amount)
                .createdBy(createdBy)
                .dateTime(dateTime)
                .type(type)
                .description(description)
                .build();
    }

    private Account findAccountById(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id %s not found", id)));
    }
}
