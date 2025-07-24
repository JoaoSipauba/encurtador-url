package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.event.publisher.UrlEventPublisher;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class AcessaUrlUseCase {

    private final UrlRepository repository;
    private final UrlEventPublisher urlEventPublisher;

    public URI executar(String shortUrl) {
        UrlEntity urlEntity = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL encurtada não encontrada"));

        urlEventPublisher.publishUrlAccessedEvent(shortUrl);
        return URI.create(urlEntity.getOriginalUrl());
    }

}
