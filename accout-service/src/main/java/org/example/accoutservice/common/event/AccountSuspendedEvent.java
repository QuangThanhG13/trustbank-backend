package org.example.accoutservice.common.event;

import lombok.Getter;
import org.example.accoutservice.commands.command.SuspendAccountCommand;
import org.example.accoutservice.common.enums.AccountStatus;

import java.time.LocalDateTime;

@Getter
public class AccountSuspendedEvent extends BaseEvent<String> {
    // việc tạm ngưng tài khoản trong mô hình Event Sourcing. Nó lưu trữ thông tin
    // quan trọng về trạng thái tài khoản (suspended = cấm hoạt động)
    private final AccountStatus status; // trạng thái tài khoản sau khi bị tạm ngưng(suspended)

    public AccountSuspendedEvent(SuspendAccountCommand command) {
        super(command.getId(), command.getCommandTime(), command.getCommandBy());
        this.status = command.getStatus();
    }

    public AccountSuspendedEvent(String id, LocalDateTime evenDate, String evenBy, AccountStatus status) {
        super(id, evenDate, evenBy);
        this.status = status;
    }
}
