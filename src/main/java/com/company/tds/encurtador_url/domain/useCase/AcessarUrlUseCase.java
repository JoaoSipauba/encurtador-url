package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.domain.event.publisher.UrlEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcessarUrlUseCase {

    private final ConsultarUrlCacheadaUseCase consultarUrlCacheadaUseCase;
    private final UrlEventPublisher urlEventPublisher;

    public URI executar(String shortUrl) {
        String OriginalUrl = consultarUrlCacheadaUseCase.execute(shortUrl);
        urlEventPublisher.publishUrlAccessedEvent(shortUrl);

        log.info("URL acessada: {} -> {}", shortUrl, OriginalUrl);
        return URI.create(OriginalUrl);
    }

}
