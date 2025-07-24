package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.exception.RecursoNaoEncontradoException;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ConsultarUrlCacheadaUseCase {
    private final UrlRepository repository;

    public ConsultarUrlCacheadaUseCase(UrlRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "urlCache", key = "#shortUrl")
    public String execute(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new RecursoNaoEncontradoException("URL encurtada não encontrada: " + shortUrl));
    }
}