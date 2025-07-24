package com.company.tds.encurtador_url.domain.event.publisher;

import com.company.tds.encurtador_url.domain.event.UrlAccessedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishUrlAccessedEvent(String shortUrl) {
        eventPublisher.publishEvent(new UrlAccessedEvent(shortUrl));
    }
}
