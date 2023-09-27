package com.swms.international.core.application;

import com.google.common.collect.Lists;
import com.swms.internatinal.api.IInternationalEntryApi;
import com.swms.internatinal.api.constants.InternationalConstants;
import com.swms.internatinal.api.constants.InternationalEntryUpdateEvent;
import com.swms.internatinal.api.dto.InternationalEntryDTO;
import com.swms.international.core.domain.entity.InternationalEntry;
import com.swms.international.core.domain.repository.InternationalEntryRepository;
import com.swms.international.core.domain.transfer.InternationalEntryTransfer;
import com.swms.mq.MqClient;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@DubboService
public class InternationalEntryApiImpl implements IInternationalEntryApi {

    @Autowired
    private InternationalEntryRepository internationalEntryRepository;

    @Autowired
    private InternationalEntryTransfer internationalEntryTransfer;

    @Autowired
    private MqClient mqClient;

    @Override
    public void createEntry(InternationalEntryDTO internationalEntryDTO) {
        internationalEntryRepository.save(internationalEntryTransfer.toDO(internationalEntryDTO));
        mqClient.sendMessage(InternationalConstants.TOPIC, new InternationalEntryUpdateEvent(
            InternationalEntryUpdateEvent.EventType.CREATE, Lists.newArrayList(internationalEntryDTO.getEntryKey())));
    }

    @Override
    public void updateEntry(InternationalEntryDTO internationalEntryDTO) {
        internationalEntryRepository.save(internationalEntryTransfer.toDO(internationalEntryDTO));
        mqClient.sendMessage(InternationalConstants.TOPIC, new InternationalEntryUpdateEvent(
            InternationalEntryUpdateEvent.EventType.UPDATE, Lists.newArrayList(internationalEntryDTO.getEntryKey())));
    }

    @Override
    public List<InternationalEntryDTO> getAllEntries() {
        List<InternationalEntry> internationalEntries = internationalEntryRepository.findAll();
        return internationalEntryTransfer.toDTOs(internationalEntries);
    }

    @Override
    public List<InternationalEntryDTO> getByEntryKeys(List<String> entryKeys) {
        return internationalEntryTransfer.toDTOs(internationalEntryRepository.findAllByEntryKeyIn(entryKeys));
    }
}
