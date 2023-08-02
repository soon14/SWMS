package com.swms.domain.event;

import com.swms.common.utils.id.IdGenerator;
import lombok.Data;

@Data
public class DomainEvent {

    private Long eventId = IdGenerator.generateId();
}
