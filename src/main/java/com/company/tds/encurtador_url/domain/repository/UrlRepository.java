package com.company.tds.encurtador_url.domain.repository;

import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends MongoRepository<UrlEntity, String> {

    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
