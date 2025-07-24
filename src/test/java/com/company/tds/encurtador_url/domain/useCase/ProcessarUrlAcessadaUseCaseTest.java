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

class ProcessarUrlAcessadaUseCaseTest {

    @Mock
    private UrlRepository urlRepository;

    private ProcessarUrlAcessadaUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new ProcessarUrlAcessadaUseCase(urlRepository);
    }

    @Test
    void incrementarContadorDeAcessosParaUrlExistente() {
        String shortUrl = "abc123";
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setAccessCount(5L);
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(urlEntity));

        useCase.executar(shortUrl);

        assertEquals(6L, urlEntity.getAccessCount());
        verify(urlRepository).save(urlEntity);
    }

    @Test
    void lancarExcecaoParaUrlNaoEncontrada() {
        String shortUrl = "nonexistent";
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> useCase.executar(shortUrl));
        verify(urlRepository, never()).save(any());
    }
}