package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.controller.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.controller.exception.ErroInternoException;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarUrlUseCase {
     private static final int SHORT_URL_LENGTH = 6;
     private static final int MAX_ATTEMPTS = 5;

     private final UrlRepository repository;

     @Value("${app.base-url:http://localhost:8080}")
     private String baseUrl;

     @CacheEvict(value = "urlCache", key = "#result.shortUrl")
     public CadastrarUrlResponse executar(CadastrarUrlRequest request) {
          String shortUrl = gerarShortUrl(request.originalUrl());
          UrlEntity entity = preencherEntity(shortUrl, request.originalUrl());

          entity = repository.save(entity);

          log.info("URL encurtada criada: {} -> {}", request.originalUrl(), entity.getShortUrl());
          return convert(entity);
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

     private String gerarShortUrl(String originalUrl) {
          String hashInput = originalUrl + System.currentTimeMillis();
          String base64Hash = generateBase64Hash(hashInput);

          int attempts = 0;
          String candidateShortUrl;
          do {
               candidateShortUrl = base64Hash.substring(0, SHORT_URL_LENGTH - 1)
                       .replaceAll("[+/=]", "") + new Random().nextInt(10);
               attempts++;
               if (attempts > MAX_ATTEMPTS) {
                    throw new ErroInternoException("Falha ao gerar URL encurtada única após " + MAX_ATTEMPTS + " tentativas");
               }
          } while (repository.findByShortUrl(candidateShortUrl).isPresent());

          return candidateShortUrl;
     }

     private String generateBase64Hash(String input) {
          try {
               MessageDigest digest = MessageDigest.getInstance("SHA-256");
               byte[] hash = digest.digest(input.getBytes());
               return Base64.getUrlEncoder().encodeToString(hash);
          } catch (NoSuchAlgorithmException e) {
               throw new ErroInternoException("Erro ao gerar hash para a URL", e);
          }
     }

     private String buildFullUrl(String shortUrl) {
          return baseUrl + "/" + shortUrl;
     }

}
