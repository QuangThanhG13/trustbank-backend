package org.example.accoutservice.queries.until.notification;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public interface NotificationService {
    void sendAccountCreationNotification(String accountId, String email, LocalDateTime dateTime);
    void sendAccountDeleteNotification(String accountId, String email, LocalDateTime dateTime);
    void sendAccountActivationNotification(String email, LocalDateTime dateTime);
    void sendAccountSuspensionNotification(String email, LocalDateTime dateTime);
    void sendAccountCreditedNotification(String email, BigDecimal amountCredited, BigDecimal balance, LocalDateTime dateTime);
    void sendAccountDebitedNotification(String email, BigDecimal amountDebited, BigDecimal balance, LocalDateTime dateTime);
}
