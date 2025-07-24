package com.company.tds.encurtador_url.controller.impl;

import com.company.tds.encurtador_url.controller.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.controller.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.controller.dto.response.VisualizarEstatisticasResponse;
import com.company.tds.encurtador_url.domain.useCase.CadastrarUrlUseCase;
import com.company.tds.encurtador_url.domain.useCase.AcessarUrlUseCase;
import com.company.tds.encurtador_url.domain.useCase.VisualizarEstatisticasUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlControllerImplTest {

    @Mock
    private CadastrarUrlUseCase cadastrarUrlUseCase;

    @Mock
    private AcessarUrlUseCase acessarUrlUseCase;

    @Mock
    private VisualizarEstatisticasUseCase visualizarEstatisticasUseCase;

    private UrlControllerImpl urlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        urlController = new UrlControllerImpl(cadastrarUrlUseCase, acessarUrlUseCase, visualizarEstatisticasUseCase);
    }

    @Test
    void retornarResponseEntityComStatusCreatedAoCadastrarUrl() {
        CadastrarUrlRequest request = new CadastrarUrlRequest("https://example.com");
        CadastrarUrlResponse response = new CadastrarUrlResponse("abc123");
        when(cadastrarUrlUseCase.executar(request)).thenReturn(response);

        ResponseEntity<CadastrarUrlResponse> result = urlController.cadastrarUrl(request);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(response, result.getBody());
    }

    @Test
    void retornarResponseEntityComStatusFoundAoAcessarUrl() {
        String shortUrl = "abc123";
        URI location = URI.create("https://example.com");
        when(acessarUrlUseCase.executar(shortUrl)).thenReturn(location);

        ResponseEntity<Void> result = urlController.acessarUrl(shortUrl);

        assertEquals(302, result.getStatusCode().value());
        assertEquals(location, result.getHeaders().getLocation());
    }

    @Test
    void retornarResponseEntityComStatusOkAoVisualizarEstatisticas() {
        String shortUrl = "abc123";
        VisualizarEstatisticasResponse response = new VisualizarEstatisticasResponse(100L, 50.0);
        when(visualizarEstatisticasUseCase.executar(shortUrl)).thenReturn(response);

        ResponseEntity<VisualizarEstatisticasResponse> result = urlController.visualizarEstatisticas(shortUrl);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(response, result.getBody());
    }
}