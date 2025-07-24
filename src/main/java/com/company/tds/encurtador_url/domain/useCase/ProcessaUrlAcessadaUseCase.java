package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessaUrlAcessadaUseCase {

    private final UrlRepository urlRepository;

    public void executar(String shortUrl) {
        var urlEntity = urlRepository.findByShortUrl(shortUrl).orElseThrow(
            () -> new IllegalArgumentException("URL encurtada não encontrada: " + shortUrl)
        );

        urlEntity.setAccessCount(urlEntity.getAccessCount() + 1);
        urlRepository.save(urlEntity);
    }
}
