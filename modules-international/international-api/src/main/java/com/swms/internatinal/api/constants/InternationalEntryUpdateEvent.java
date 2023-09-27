package com.swms.internatinal.api.constants;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class InternationalEntryUpdateEvent {

    private EventType eventType;

    private List<String> entryKeys;

    public InternationalEntryUpdateEvent(EventType eventType, List<String> entryKeys) {
        this.eventType = eventType;
        this.entryKeys = entryKeys;
    }

    public enum EventType {
        CREATE, UPDATE, DELETE;
    }
}
