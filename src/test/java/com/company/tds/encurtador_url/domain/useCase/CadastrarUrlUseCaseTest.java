package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.controller.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.controller.exception.ErroInternoException;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarUrlUseCaseTest {

    @Mock
    private UrlRepository repository;

    private CadastrarUrlUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CadastrarUrlUseCase(repository);

        ReflectionTestUtils.setField(useCase, "baseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(useCase, "algoritmo", "SHA-256");
    }

    @Test
    void criarUrlEncurtadaComSucesso() {
        String originalUrl = "https://example.com";
        CadastrarUrlRequest request = new CadastrarUrlRequest(originalUrl);
        UrlEntity savedEntity = UrlEntity.builder()
                .shortUrl("abc123")
                .originalUrl(originalUrl)
                .accessCount(0L)
                .createdAt(null)
                .build();
        when(repository.save(any(UrlEntity.class))).thenReturn(savedEntity);

        CadastrarUrlResponse response = useCase.executar(request);

        assertEquals("http://localhost:8080/abc123", response.getShortUrl());
        verify(repository).save(any(UrlEntity.class));
    }

    @Test
    void falharAoGerarUrlEncurtadaUnicaAposMaximoDeTentativas() {
        String originalUrl = "https://example.com";
        CadastrarUrlRequest request = new CadastrarUrlRequest(originalUrl);
        when(repository.findByShortUrl(anyString())).thenReturn(Optional.of(new UrlEntity()));

        assertThrows(ErroInternoException.class, () -> useCase.executar(request));
    }

    @Test
    void falharAoGerarHashParaUrl() {
        ReflectionTestUtils.setField(useCase, "algoritmo", "none");

        String originalUrl = "https://example.com";
        CadastrarUrlRequest request = new CadastrarUrlRequest(originalUrl);
        CadastrarUrlUseCase spyUseCase = spy(useCase);

        assertThrows(ErroInternoException.class, () -> spyUseCase.executar(request));
    }
}