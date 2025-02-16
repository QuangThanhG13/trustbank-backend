package org.example.accoutservice.commands.util.proxy;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.accoutservice.commands.command.DebitAccountCommand;
import org.example.accoutservice.commands.dto.TranferRequestDTO;
import org.example.accoutservice.commands.util.factory.CommandFactory;
import org.example.accoutservice.common.security.SecurityInformation;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class TransferProxy {
    public TransferProxy() {
    }

    public List<CompletableFuture<String>> transfer(TranferRequestDTO dto,
                                                    @NotNull CommandGateway commandGateway,
                                                    @NotNull SecurityInformation securityInformation) {
        log.info("TransferProxy request:{}", dto);

//        DebitAccountCommand debitCommand = CommandFactory.createDebitAccountCommand(dto.ge, securityInformation.getUsername())
        return null;
    }

}
