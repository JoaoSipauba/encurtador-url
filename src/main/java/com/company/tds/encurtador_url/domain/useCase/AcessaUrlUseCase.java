package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.exception.RecursoNaoEncontradoException;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.event.publisher.UrlEventPublisher;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcessaUrlUseCase {

    private final UrlRepository repository;
    private final UrlEventPublisher urlEventPublisher;

    public URI executar(String shortUrl) {
        UrlEntity urlEntity = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RecursoNaoEncontradoException("URL encurtada não encontrada"));

        urlEventPublisher.publishUrlAccessedEvent(shortUrl);

        log.info("URL acessada: {} -> {}", shortUrl, urlEntity.getOriginalUrl());
        return URI.create(urlEntity.getOriginalUrl());
    }

}
