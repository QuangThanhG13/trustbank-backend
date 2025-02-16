package org.example.accoutservice.commands.web;

import org.example.accoutservice.commands.dto.CustomerExistResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "CUSTOMER-SERVICE")

public interface CustomerRestClient {

    @GetMapping("bank/intern/verify/{id}")
    CustomerExistResponseDTO checkCustomerExist(String id);
}
