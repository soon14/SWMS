package com.swms.domain.event.domain.repository;

import com.swms.domain.event.domain.DomainEventPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainEventPORepository extends JpaRepository<DomainEventPO, Long> {

}
