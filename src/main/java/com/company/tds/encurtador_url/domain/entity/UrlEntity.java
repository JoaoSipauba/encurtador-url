package com.company.tds.encurtador_url.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "urls")
public class UrlEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String shortUrl;

    private String originalUrl;

    private Long accessCount;

    private LocalDateTime createdAt;
}
