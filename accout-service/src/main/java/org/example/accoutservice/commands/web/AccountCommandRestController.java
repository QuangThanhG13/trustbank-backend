package org.example.accoutservice.commands.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.accoutservice.commands.command.*;
import org.example.accoutservice.commands.dto.*;
import org.example.accoutservice.commands.exception.CustomerNotFoundException;
import org.example.accoutservice.commands.util.factory.CommandFactory;
import org.example.accoutservice.commands.util.generator.IdGenerator;
import org.example.accoutservice.commands.util.proxy.TransferProxy;
import org.example.accoutservice.common.enums.AccountStatus;
import org.example.accoutservice.common.enums.Currency;
import org.example.accoutservice.common.security.SecurityInformation;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts/commands")
//@EnableFeignClients
public class AccountCommandRestController {

    private final CommandGateway commandGateway;
    private final CustomerRestClient customerRestClient;
    private final IdGenerator idGenerator;
    private final SecurityInformation securityInformation;


    public AccountCommandRestController(CommandGateway commandGateway, CustomerRestClient customerRestClient,
                                        IdGenerator idGenerator, SecurityInformation securityInformation) {
        this.commandGateway = commandGateway;
        this.customerRestClient = customerRestClient;
        this.idGenerator = idGenerator;
        this.securityInformation = securityInformation;
    }

    @PostMapping("/create")
    public CompletableFuture<String> create(@RequestBody @Valid AccountRequestDTO dto) {
        CustomerExistResponseDTO customer = checkCustomerExist(dto.customerId());
        if (customer == null) {
            throw new CustomerNotFoundException(String.format("Customer with id %s not found", dto.customerId()));
        }
        CreateAccountCommand command = createCommand(dto.customerId(), customer.email(), dto.currency());
        return commandGateway.send(command);
    }

    @PutMapping("/update")
    public CompletableFuture<String> update(@RequestBody @Valid UpdateStatusRequestDTO dto) {
        if (dto.accountStatus().equals(AccountStatus.ACTIVATED)) {
            ActiveAccountCommand command = CommandFactory.createActiveAccountCommand(dto, securityInformation.getUsername());
            return commandGateway.send(command);
        } else if (dto.accountStatus().equals(AccountStatus.SUSPENDED)) {
            SuspendAccountCommand command = CommandFactory.createSuspendAccountCommand(dto, securityInformation.getUsername());
            return commandGateway.send(command);
        }else {
            throw new IllegalArgumentException("Unsupported account status" + dto.accountStatus());
        }
    }

    @PostMapping("/credit")
    public CompletableFuture<String> credit(@RequestBody @Valid OperationRequestDTO dto) {
        CreditAccountCommand command = CommandFactory.createCreditAccountCommand(dto, securityInformation.getUsername());
        return commandGateway.send(command);
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debit(@RequestBody @Valid OperationRequestDTO dto) {
        DebitAccountCommand command = CommandFactory.createDebitAccountCommand(dto, securityInformation.getUsername());
        return commandGateway.send(command);
    }

    @PostMapping("/transfer")
    public List<CompletableFuture<String>> transfer(@RequestBody @Valid TranferRequestDTO dto) {
        TransferProxy proxy = new TransferProxy();
        return proxy.transfer(dto, commandGateway, securityInformation);
    }

    @DeleteMapping("/delete/{id}")
    public CompletableFuture<String> delete(@PathVariable String id) {
        DeleteAccountCommand command = CommandFactory.createDeleteAccountCommand(id, securityInformation.getUsername());
        return commandGateway.send(command);
    }

    @Nullable
    private CustomerExistResponseDTO checkCustomerExist(String customerId) {
        try {
            return customerRestClient.checkCustomerExist(customerId);
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    @Contract("_, _, _ -> new")
    private CreateAccountCommand createCommand(String customerId, String email, Currency currency) {
        return new CreateAccountCommand(
                idGenerator.autoGenerateId(),
                LocalDateTime.now(),
                AccountStatus.CREATED,
                BigDecimal.ZERO,
                currency,
                securityInformation.getUsername(),
                customerId,
                email
        );
    }
}
