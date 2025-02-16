package org.example.accoutservice.queries.web;

import jakarta.validation.Valid;
import org.example.accoutservice.queries.dto.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient("NOTIFICATION-SERVICE")
@Component
public interface NotificationRestClient {

    @PostMapping("/bank/mailing/send")
    void sendNotification(@RequestBody @Valid NotificationRequestDTO notification);
}
