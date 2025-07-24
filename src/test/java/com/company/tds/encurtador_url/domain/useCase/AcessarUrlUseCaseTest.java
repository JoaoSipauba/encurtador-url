package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.exception.RecursoNaoEncontradoException;
import com.company.tds.encurtador_url.domain.event.publisher.UrlEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AcessarUrlUseCaseTest {

    @Mock
    private ConsultarUrlCacheadaUseCase consultarUrlCacheadaUseCase;

    @Mock
    private UrlEventPublisher urlEventPublisher;

    private AcessarUrlUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new AcessarUrlUseCase(consultarUrlCacheadaUseCase, urlEventPublisher);
    }

    @Test
    void retornarUriParaShortUrlValida() {
        String shortUrl = "abc123";
        String originalUrl = "https://example.com";
        when(consultarUrlCacheadaUseCase.execute(shortUrl)).thenReturn(originalUrl);

        URI result = useCase.executar(shortUrl);

        assertEquals(URI.create(originalUrl), result);
        verify(urlEventPublisher).publishUrlAccessedEvent(shortUrl);
    }

    @Test
    void lancarExcecaoParaShortUrlInvalida() {
        String shortUrl = "invalid";
        when(consultarUrlCacheadaUseCase.execute(shortUrl)).thenThrow(new RecursoNaoEncontradoException("URL não encontrada"));

        assertThrows(RecursoNaoEncontradoException.class, () -> useCase.executar(shortUrl));
        verify(urlEventPublisher, never()).publishUrlAccessedEvent(shortUrl);
    }
}