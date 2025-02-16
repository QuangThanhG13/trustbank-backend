package org.example.accoutservice.commands.util.factory;

import jakarta.validation.constraints.NotNull;
import org.example.accoutservice.commands.command.*;
import org.example.accoutservice.commands.dto.OperationRequestDTO;
import org.example.accoutservice.commands.dto.TranferRequestDTO;
import org.example.accoutservice.commands.dto.UpdateStatusRequestDTO;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.enums.OperationType;
import org.jetbrains.annotations.Contract;

import java.time.LocalDateTime;

public class CommandFactory {

    public CommandFactory() {
     super();
    }

    @NotNull
    @Contract("_, _ -> new")
    public static CreditAccountCommand createCreditAccountCommand(@NotNull OperationRequestDTO dto,
                                                                  final String userName) {
        return new CreditAccountCommand(
                dto.accountId(),
                LocalDateTime.now(),
                userName,
                dto.amount(),
                OperationType.CREDIT,
                dto.description()
        );
    }

    @NotNull
    @Contract("_, _ -> new")
    public static DebitAccountCommand createDebitAccountCommand(@NotNull OperationRequestDTO dto,
                                                                final String userName) {
        return new DebitAccountCommand(
                dto.accountId(),
                LocalDateTime.now(),
                userName,
                dto.amount(),
                OperationType.DEBIT,
                dto.description()
        );
    }


    @NotNull
    @Contract("_, _ -> new")
    public static ActiveAccountCommand createActiveAccountCommand (@NotNull UpdateStatusRequestDTO dto,
                                                                   final String userName) {
        return new ActiveAccountCommand(
                dto.accountId(),
                AccountStatus.ACTIVATED,
                LocalDateTime.now(),
                userName
        );
    }

    @NotNull
    @Contract("_, _ -> new")
    public static SuspendAccountCommand createSuspendAccountCommand(@NotNull UpdateStatusRequestDTO dto,
                                                                    final String userName) {
        return new SuspendAccountCommand(
                dto.accountId(),
                LocalDateTime.now(),
                userName,
                AccountStatus.SUSPENDED
        );
    }

    @NotNull
    @Contract("_, _ -> new")
    public static DeleteAccountCommand createDeleteAccountCommand(@NotNull String accountId,
                                                                  String userName) {
        return new DeleteAccountCommand(
                accountId,
                LocalDateTime.now(),
                userName
        );
    }

    @NotNull
    @Contract("_, _ -> new")
    public static DebitAccountCommand createCreditAccountCommand(@NotNull TranferRequestDTO dto,
                                                                 final String userName) {
        return new DebitAccountCommand(
                dto.accountIdFrom(),
                LocalDateTime.now(),
                userName,
                dto.amount(),
                OperationType.DEBIT,
                dto.description()
        );
    }

    @NotNull
    @Contract("_, _ -> new")
    public static CreditAccountCommand createDebitAccountCommand(@NotNull TranferRequestDTO dto,
                                                                 final String userName) {
        return new CreditAccountCommand(
                dto.accountIdTo(),
                LocalDateTime.now(),
                userName,
                dto.amount(),
                OperationType.CREDIT,
                dto.description()
        );
    }

    @NotNull
    @Contract("_, _ -> new")
    public static CreditAccountCommand createCreditAccountCommandReverse(@NotNull TranferRequestDTO dto,
                                                                         final String userName) {
        return new CreditAccountCommand(
                dto.accountIdFrom(),
                LocalDateTime.now(),
                userName,
                dto.amount(),
                OperationType.CREDIT,
                dto.description()
        );
    }
}
