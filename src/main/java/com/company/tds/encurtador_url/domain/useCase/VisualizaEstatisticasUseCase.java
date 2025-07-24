package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.dto.response.VisualizarEstatisticasResponse;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisualizaEstatisticasUseCase {

    private final UrlRepository repository;

    public VisualizarEstatisticasResponse executar(String shortUrl) {
        UrlEntity urlEntity = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL encurtada não encontrada"));

        return new VisualizarEstatisticasResponse(
                urlEntity.getAccessCount(),
                calcularMediaAcessosPorDia(urlEntity.getCreatedAt(), urlEntity.getAccessCount())
        );
    }

    private Double calcularMediaAcessosPorDia(LocalDateTime createdAt, Long accessCount) {
        long daysSinceCreation = LocalDateTime.now().toLocalDate().toEpochDay() - createdAt.toLocalDate().toEpochDay();
        if (daysSinceCreation == 0) {
            return (double) accessCount;
        }
        return (double) accessCount / daysSinceCreation;
    }
}
