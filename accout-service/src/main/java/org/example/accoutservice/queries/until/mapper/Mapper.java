package org.example.accoutservice.queries.until.mapper;

import jakarta.validation.constraints.NotNull;
import org.example.accoutservice.queries.dto.AccountResponseDTO;
import org.example.accoutservice.queries.dto.OperationResponseDTO;
import org.example.accoutservice.queries.entity.Account;
import org.example.accoutservice.queries.entity.Operation;

import java.util.List;

public class Mapper {

    private Mapper() {

    }

    @NotNull
    public static AccountResponseDTO fromAccount(@NotNull final Account account) {
        final AccountResponseDTO dto = new AccountResponseDTO();
        dto.setId(account.getId());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setStatus(account.getStatus());
        dto.setCustomerId(account.getCustomerId());
        dto.setCreatedBy(account.getCreatedBy());
        dto.setCreatedDate(account.getCreatedDate());
        dto.setLastModifiedBy(account.getLastModifiedBy());
        dto.setLastModifiedDate(account.getLastModifiedDate());
        return dto;
    }

    public static List<AccountResponseDTO> fromAccounts(@NotNull final List<Account> accounts) {
        return accounts.stream().map(Mapper :: fromAccount).toList();
    }

    public static OperationResponseDTO fromOperation(@NotNull final Operation operation) {
        final OperationResponseDTO dto = new OperationResponseDTO();
        dto.setId(operation.getId());
        dto.setAmount(operation.getAmount());
        dto.setDescription(operation.getDescription());
        dto.setCreatedBy(operation.getCreatedBy());
        dto.setType(operation.getType());
        dto.setDateTime(operation.getDateTime());
        dto.setAccountId(getAccountId(operation.getAccount()));
        return dto;
    }

    public static List<OperationResponseDTO> fromOperations(@NotNull final List<Operation> operations) {
        return operations.stream().map(Mapper :: fromOperation).toList();
    }

    private static String getAccountId(final Account account) {
        if (account == null) {
            return null;
        }
        return account.getId();
    }
}
