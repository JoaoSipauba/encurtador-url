package com.company.tds.encurtador_url.domain.event.publisher;

import com.company.tds.encurtador_url.domain.event.UrlAccessedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;

class UrlEventPublisherTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private UrlEventPublisher urlEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        urlEventPublisher = new UrlEventPublisher(eventPublisher);
    }

    @Test
    void publicarEventoDeAcessoComShortUrlValida() {
        String shortUrl = "abc123";

        urlEventPublisher.publishUrlAccessedEvent(shortUrl);

        verify(eventPublisher).publishEvent(new UrlAccessedEvent(shortUrl));
    }

    @Test
    void naoPublicarEventoQuandoShortUrlForNula() {
        String shortUrl = null;

        urlEventPublisher.publishUrlAccessedEvent(shortUrl);

        verify(eventPublisher, never()).publishEvent(any());
    }
}