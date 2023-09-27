package com.swms.international.sdk.consumer;

import com.swms.internatinal.api.constants.InternationalConstants;
import com.swms.internatinal.api.constants.InternationalEntryUpdateEvent;
import com.swms.international.sdk.client.I18nApi;
import com.swms.mq.redis.RedisListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class I18nConsumer {

    @Autowired
    private I18nApi i18nApi;

    @RedisListener(topic = InternationalConstants.TOPIC, type = InternationalEntryUpdateEvent.class)
    public void listPluginManage(String topic, InternationalEntryUpdateEvent internationalEntryUpdateEvent) {
        if (internationalEntryUpdateEvent == null) {
            return;
        }

        switch (internationalEntryUpdateEvent.getEventType()) {
            case CREATE, UPDATE:
                i18nApi.setEntry(internationalEntryUpdateEvent.getEntryKeys());
                break;
            case DELETE:
                i18nApi.removeEntry(internationalEntryUpdateEvent.getEntryKeys());
                break;
            default:
                break;
        }

    }
}
