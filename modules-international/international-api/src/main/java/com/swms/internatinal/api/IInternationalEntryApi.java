package com.swms.internatinal.api;

import com.swms.internatinal.api.dto.InternationalEntryDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface IInternationalEntryApi {

    void createEntry(@Valid InternationalEntryDTO internationalEntryDTO);

    void updateEntry(@Valid InternationalEntryDTO internationalEntryDTO);

    List<InternationalEntryDTO> getAllEntries();

    List<InternationalEntryDTO> getByEntryKeys(List<String> entryKeys);
}
