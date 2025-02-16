package org.example.accoutservice.queries.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.accoutservice.queries.dto.AccountResponseDTO;
import org.example.accoutservice.queries.dto.OperationResponseDTO;
import org.example.accoutservice.queries.entity.Account;
import org.example.accoutservice.queries.entity.Operation;
import org.example.accoutservice.queries.query.GetAccountByCustomerIdQuery;
import org.example.accoutservice.queries.query.GetAccountByIdQuery;
import org.example.accoutservice.queries.query.GetOperationByAccountId;
import org.example.accoutservice.queries.query.GetOperationByIdQuery;
import org.example.accoutservice.queries.respository.AccountRepository;
import org.example.accoutservice.queries.respository.OperationRepository;
import org.example.accoutservice.queries.until.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountQueryHandlerService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public AccountQueryHandlerService(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public AccountResponseDTO handle(@NotNull GetAccountByIdQuery query) {
        log.info("GetAccountByIdQuery received");
        Account account = accountRepository.findById(query.getAccountId()).orElse(null);
        if (account == null) {
            log.warn("Account with id {} not found", query.getAccountId());
            return null;
        }
        log.info("Account with customer's id {} found", query.getAccountId());
        return Mapper.fromAccount(account);
    }

    @QueryHandler
    public AccountResponseDTO handle(@NotNull GetAccountByCustomerIdQuery query) {
        log.info("GetAccountByCustomerIdQuery received");
        Account account = accountRepository.findByCustomerId(query.getCustomerId()).orElse(null);
        if (account == null) {
            log.warn("Account with customer's id {} not found", query.getCustomerId());
            return null;
        }
        return Mapper.fromAccount(account);
    }

    @QueryHandler
    public OperationResponseDTO handle(@NotNull GetOperationByIdQuery query) {
        log.info("GetOperationByIdQuery received");
        Operation operation = operationRepository.findById(query.getOperationId()).orElse(null);
        if (operation == null) {
            log.warn("Operation with id {} not found", query.getOperationId());
            return null;
        }
        return Mapper.fromOperation(operation);
    }

    @QueryHandler
    public List<OperationResponseDTO> handle(@NotNull GetOperationByAccountId query) {
        log.info("GetOperationByAccountId handled");
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
        Page<Operation> operationPage = operationRepository.findByAccountId(query.getAccountId(), pageable);
        log.info("Operations found for account {}", query.getAccountId());
        return Mapper.fromOperations(operationPage.getContent());
    }

}
