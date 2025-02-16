package org.example.accoutservice.common.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseEvent<T> {
    private final T id;
    private final LocalDateTime eventDate; //Thời gian sự kiện xảy ra.
    private final String enventBy; //Ai đã thực hiện sự kiện này.

    public BaseEvent (T id, LocalDateTime eventDate, String eventBy) {
        this.id = id;
        this.eventDate = eventDate;
        this.enventBy = eventBy;
    }
}
