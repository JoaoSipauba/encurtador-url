package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.dto.response.VisualizarEstatisticasResponse;
import com.company.tds.encurtador_url.controller.exception.RecursoNaoEncontradoException;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VisualizarEstatisticasUseCaseTest {

    @Mock
    private UrlRepository repository;

    private VisualizarEstatisticasUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new VisualizarEstatisticasUseCase(repository);
    }

    @Test
    void executarReturnsCorrectStatisticsForValidShortUrl() {
        String shortUrl = "abc123";
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setAccessCount(100L);
        urlEntity.setCreatedAt(LocalDateTime.now().minusDays(10));
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.of(urlEntity));

        VisualizarEstatisticasResponse response = useCase.executar(shortUrl);

        assertEquals(100L, response.getTotalAccesses());
        assertEquals(10.0, response.getAverageAccessesPerDay());
    }

    @Test
    void executarThrowsExceptionForNonExistentShortUrl() {
        String shortUrl = "nonexistent";
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> useCase.executar(shortUrl));
    }

    @Test
    void executarCalculatesAverageAccessPerDayCorrectlyForSameDayCreation() {
        String shortUrl = "sameDay";
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setAccessCount(50L);
        urlEntity.setCreatedAt(LocalDateTime.now());
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.of(urlEntity));

        VisualizarEstatisticasResponse response = useCase.executar(shortUrl);

        assertEquals(50.0, response.getAverageAccessesPerDay());
    }

    @Test
    void executarHandlesZeroAccessCountCorrectly() {
        String shortUrl = "zeroAccess";
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setAccessCount(0L);
        urlEntity.setCreatedAt(LocalDateTime.now().minusDays(5));
        when(repository.findByShortUrl(shortUrl)).thenReturn(Optional.of(urlEntity));

        VisualizarEstatisticasResponse response = useCase.executar(shortUrl);

        assertEquals(0L, response.getTotalAccesses());
        assertEquals(0.0, response.getAverageAccessesPerDay());
    }
}