package com.swms.utils.event;

import com.swms.utils.id.IdGenerator;
import lombok.Data;

@Data
public class DomainEvent {

    private Long eventId = IdGenerator.generateId();
}
