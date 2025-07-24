package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.exception.RecursoNaoEncontradoException;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultarUrlCacheadaUseCaseTest {

    @Mock
    private UrlRepository repository;

    private ConsultarUrlCacheadaUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new ConsultarUrlCacheadaUseCase(repository);
    }

    @Test
    void retornarUrlOriginalParaShortUrlExistente() {
        String shortUrl = "abc123";
        String originalUrl = "https://example.com";
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.of(urlEntity));

        String result = useCase.execute(shortUrl);

        assertEquals(originalUrl, result);
    }

    @Test
    void lancarExcecaoParaShortUrlNaoEncontrada() {
        String shortUrl = "nonexistent";
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> useCase.execute(shortUrl));
    }
}