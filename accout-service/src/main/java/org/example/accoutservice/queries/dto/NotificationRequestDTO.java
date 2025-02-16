package org.example.accoutservice.queries.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NotificationRequestDTO {
    @Email(message = "e-mail is not well formated")
    @NotBlank(message = "e-mail is mandatory: it can not be blank")
    String to;

    @NotBlank(message = "subject is mandatory: it can not be blank")
    String subject;

    @NotBlank(message = "body is mandatory: it can not be blank")
    String body;

    public NotificationRequestDTO(String email, String title, String body) {
    }
}
