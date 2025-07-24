package com.company.tds.encurtador_url.domain.event.publisher;

import com.company.tds.encurtador_url.domain.event.UrlAccessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Async
    public void publishUrlAccessedEvent(String shortUrl) {
        log.info("Publicando UrlAccessedEvent, shortUrl: {}", shortUrl);
        eventPublisher.publishEvent(new UrlAccessedEvent(shortUrl));
    }
}
