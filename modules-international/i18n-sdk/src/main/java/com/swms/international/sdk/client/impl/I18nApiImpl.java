package com.swms.international.sdk.client.impl;

import com.google.common.collect.Maps;
import com.swms.common.utils.utils.TenantContext;
import com.swms.internatinal.api.IInternationalEntryApi;
import com.swms.internatinal.api.dto.InternationalEntryDTO;
import com.swms.international.sdk.client.I18nApi;
import com.swms.international.sdk.domain.entity.TenantInternationalEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class I18nApiImpl implements I18nApi {

    private static final Map<String, TenantInternationalEntry> tenantEntryMap = Maps.newConcurrentMap();

    @DubboReference
    private IInternationalEntryApi internationalEntryApi;

    @Override
    public void setEntry(InternationalEntryDTO internationalEntryDTO) {
        TenantInternationalEntry internationalEntry = tenantEntryMap.get(TenantContext.getTenant());
        if (internationalEntry == null) {
            return;
        }
        internationalEntry.setEntry(internationalEntryDTO);
    }

    @Override
    public String getEntryValue(String entryKey, String language) {

        if (tenantEntryMap.containsKey(TenantContext.getTenant())) {
            return tenantEntryMap.get(TenantContext.getTenant()).getEntryValue(entryKey, language);
        }

        List<InternationalEntryDTO> allEntries = internationalEntryApi.getAllEntries();
        if (CollectionUtils.isEmpty(allEntries)) {
            log.error("there are no entries initialized, please initialize international entries");
            return null;
        }

        tenantEntryMap.put(TenantContext.getTenant(), new TenantInternationalEntry(TenantContext.getTenant(),
            allEntries.stream().collect(Collectors.toMap(InternationalEntryDTO::getEntryKey, v -> v))));
        return tenantEntryMap.get(TenantContext.getTenant()).getEntryValue(entryKey, language);
    }

    @Override
    public void setEntry(List<String> entryKeys) {
        List<InternationalEntryDTO> internationalEntryDTOS = internationalEntryApi.getByEntryKeys(entryKeys);

        if (CollectionUtils.isEmpty(internationalEntryDTOS)) {
            return;
        }

        internationalEntryDTOS.forEach(this::setEntry);
    }

    @Override
    public void removeEntry(List<String> entryKeys) {
        if (CollectionUtils.isEmpty(entryKeys)) {
            return;
        }
        TenantInternationalEntry tenantInternationalEntry = tenantEntryMap.get(TenantContext.getTenant());
        if (tenantInternationalEntry == null) {
            return;
        }
        tenantInternationalEntry.removeEntry(entryKeys);
    }
}
