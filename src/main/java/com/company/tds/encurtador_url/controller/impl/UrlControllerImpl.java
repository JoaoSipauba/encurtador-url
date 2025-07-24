package com.company.tds.encurtador_url.controller.impl;

import com.company.tds.encurtador_url.controller.UrlController;
import com.company.tds.encurtador_url.controller.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.controller.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.controller.dto.response.VisualizarEstatisticasResponse;
import com.company.tds.encurtador_url.domain.useCase.CadastrarUrlUseCase;
import com.company.tds.encurtador_url.domain.useCase.AcessarUrlUseCase;
import com.company.tds.encurtador_url.domain.useCase.VisualizarEstatisticasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class UrlControllerImpl implements UrlController {

    private final CadastrarUrlUseCase cadastrarUrlUseCase;
    private final AcessarUrlUseCase acessarUrlUseCase;
    private final VisualizarEstatisticasUseCase visualizarEstatisticasUseCase;

    @Override
    public ResponseEntity<CadastrarUrlResponse> cadastrarUrl(CadastrarUrlRequest request) {
        var response = cadastrarUrlUseCase.executar(request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    public ResponseEntity<Void> acessarUrl(String shortUrl) {
        var response = acessarUrlUseCase.executar(shortUrl);
        return ResponseEntity.status(FOUND).location(response).build();
    }

    @Override
    public ResponseEntity<VisualizarEstatisticasResponse> visualizarEstatisticas(String shortUrl) {
        var response = visualizarEstatisticasUseCase.executar(shortUrl);
        return ResponseEntity.status(OK).body(response);
    }
}
