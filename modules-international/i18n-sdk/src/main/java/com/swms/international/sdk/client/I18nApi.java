package com.swms.international.sdk.client;

import com.swms.internatinal.api.dto.InternationalEntryDTO;

import java.util.List;

public interface I18nApi {

    void setEntry(InternationalEntryDTO internationalEntryDTO);

    String getEntryValue(String entryKey, String language);

    void setEntry(List<String> entryKeys);

    void removeEntry(List<String> entryKeys);
}
