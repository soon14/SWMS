package com.swms.international.sdk.domain.entity;

import com.swms.internatinal.api.dto.InternationalEntryDTO;

import java.util.List;
import java.util.Map;

public class TenantInternationalEntry {

    private String tenantId;
    private Map<String, InternationalEntryDTO> entryMap;

    public TenantInternationalEntry(String tenantId, Map<String, InternationalEntryDTO> entryMap) {
        this.tenantId = tenantId;
        this.entryMap = entryMap;
    }

    public String getEntryValue(String entryKey, String language) {
        if (entryMap == null) {
            return null;
        }

        InternationalEntryDTO internationalEntryDTO = entryMap.get(entryKey);
        if (internationalEntryDTO == null) {
            return null;
        }

        return internationalEntryDTO.getEntryValue(language);
    }

    public void setEntry(InternationalEntryDTO internationalEntryDTO) {
        if (entryMap == null) {
            return;
        }
        entryMap.put(internationalEntryDTO.getEntryKey(), internationalEntryDTO);
    }

    public void removeEntry(List<String> entryKeys) {
        if (entryMap == null) {
            return;
        }
        entryKeys.forEach(entryKey -> entryMap.remove(entryKey));
    }
}
