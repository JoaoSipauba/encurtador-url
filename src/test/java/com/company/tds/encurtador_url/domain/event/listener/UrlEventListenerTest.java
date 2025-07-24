package com.company.tds.encurtador_url.domain.event.listener;

import com.company.tds.encurtador_url.domain.event.UrlAccessedEvent;
import com.company.tds.encurtador_url.domain.useCase.ProcessarUrlAcessadaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UrlEventListenerTest {

    @Mock
    private ProcessarUrlAcessadaUseCase processarUrlAcessadaUseCase;

    private UrlEventListener urlEventListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        urlEventListener = new UrlEventListener(processarUrlAcessadaUseCase);
    }

    @Test
    void processarEventoDeUrlAcessadaComSucesso() {
        UrlAccessedEvent event = new UrlAccessedEvent("abc123");

        urlEventListener.onUrlAccessedEvent(event);

        verify(processarUrlAcessadaUseCase).executar("abc123");
    }
}