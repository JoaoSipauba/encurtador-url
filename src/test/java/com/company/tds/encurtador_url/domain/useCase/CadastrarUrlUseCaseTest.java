package com.company.tds.encurtador_url.domain.useCase;

import com.company.tds.encurtador_url.controller.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.controller.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.domain.entity.UrlEntity;
import com.company.tds.encurtador_url.domain.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarUrlUseCaseTest {

    @Mock
    private UrlRepository repository;

    private CadastrarUrlUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CadastrarUrlUseCase(repository);

        ReflectionTestUtils.setField(useCase, "baseUrl", "http://localhost:8080");
    }

    @Test
    void criarUrlEncurtadaComSucesso() {
        String originalUrl = "https://example.com";
        CadastrarUrlRequest request = new CadastrarUrlRequest(originalUrl);
        UrlEntity savedEntity = UrlEntity.builder()
                .shortUrl("abc123")
                .originalUrl(originalUrl)
                .accessCount(0L)
                .createdAt(null)
                .build();
        when(repository.save(any(UrlEntity.class))).thenReturn(savedEntity);

        CadastrarUrlResponse response = useCase.executar(request);

        assertEquals("http://localhost:8080/abc123", response.getShortUrl());
        verify(repository).save(any(UrlEntity.class));
    }

    @Test
    void deveGerarShortUrlComComprimentoCorreto() {
        String shortUrl = useCase.gerarShortUrl();
        assertEquals(6, shortUrl.length(), "Short URL deve ter comprimento de 6 caracteres");
    }

    @Test
    void deveGerarShortUrlComValorZero() {
        String shortUrl = useCase.encodeToBase62WithRandomness(0, 6);
        assertEquals(6, shortUrl.length(), "Short URL deve ter comprimento de 6 caracteres");
        for (char c : shortUrl.toCharArray()) {
            assertTrue(CadastrarUrlUseCase.BASE62_CHARS.indexOf(c) >= 0, "Caracter deve estar dentro do conjunto BASE62");
        }
    }

    @Test
    void deveRejeitarUrlsInvalidas() {
        List<String> urlsInvalidas = List.of(
                "www.google.com",
                "google.com/teste",
                "://site.com",
                "http//site.com",
                "http://",
                "http:///pagina",
                "http://.com",
                "http://-dominio.com",
                "http://999.999.999.999",
                "http://256.256.256.256",
                "http://meu site.com",
                "http://exemplo.com/algum caminho",
                "invalid_url",
                "justtext",
                "notaurl://blabla"
        );

        for (String url : urlsInvalidas) {
            CadastrarUrlRequest request = new CadastrarUrlRequest(url);
            assertThrows(IllegalArgumentException.class, () -> useCase.executar(request),
                    "Deveria lançar exceção para URL inválida: " + url);
        }
    }
}