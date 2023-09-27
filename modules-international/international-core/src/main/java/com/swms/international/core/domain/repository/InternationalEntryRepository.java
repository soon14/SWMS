package com.swms.international.core.domain.repository;

import com.swms.international.core.domain.entity.InternationalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternationalEntryRepository extends JpaRepository<InternationalEntry, Long> {
    List<InternationalEntry> findAllByEntryKeyIn(List<String> entryKeys);
}
