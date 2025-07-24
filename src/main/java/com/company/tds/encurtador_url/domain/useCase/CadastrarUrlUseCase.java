package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.controller.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarUrlUseCase {
     private static final int SHORT_URL_LENGTH = 6;
     static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

     private final UrlRepository repository;

     @Value("${app.base-url:http://localhost:8080}")
     private String baseUrl;

     @CacheEvict(value = "urlCache", key = "#result.shortUrl")
     public CadastrarUrlResponse executar(CadastrarUrlRequest request) {
          validarUrl(request.originalUrl());

          String shortUrl = gerarShortUrl();
          UrlEntity entity = preencherEntity(shortUrl, request.originalUrl());

          entity = repository.save(entity);

          log.info("URL encurtada criada: {} -> {}", request.originalUrl(), entity.getShortUrl());
          return convert(entity);
     }

     private void validarUrl(String originalUrl) {
          UrlValidator validator = new UrlValidator();
          if (!validator.isValid(originalUrl)) {
               throw new IllegalArgumentException("URL original inválida: " + originalUrl);
          }
     }

     private CadastrarUrlResponse convert(UrlEntity entity) {
          return new CadastrarUrlResponse(buildFullUrl(entity.getShortUrl()));
     }

     private UrlEntity preencherEntity(String shortUrl, String originalUrl) {
          return UrlEntity.builder()
                  .shortUrl(shortUrl)
                  .originalUrl(originalUrl)
                  .accessCount(0L)
                  .createdAt(LocalDateTime.now())
                  .build();
     }

     String gerarShortUrl() {
          long counter = repository.count() + 1;
          long randomSeed = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
          long combinedValue = counter ^ randomSeed;
          return encodeToBase62WithRandomness(combinedValue, SHORT_URL_LENGTH);
     }

     String encodeToBase62WithRandomness(long value, int length) {
          StringBuilder shortUrl = new StringBuilder();
          long workingValue = value;

          for (int i = 0; i < length; i++) {
               if (workingValue > 0) {
                    shortUrl.append(BASE62_CHARS.charAt((int) (workingValue % 62)));
                    workingValue /= 62;
               } else {
                    shortUrl.append(BASE62_CHARS.charAt(ThreadLocalRandom.current().nextInt(62)));
               }
          }

          return shortUrl.toString();
     }

     private String buildFullUrl(String shortUrl) {
          return baseUrl + "/" + shortUrl;
     }

}
