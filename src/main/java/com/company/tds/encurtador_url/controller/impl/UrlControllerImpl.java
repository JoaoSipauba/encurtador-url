package com.company.tds.encurtador_url.controller.impl;

import com.company.tds.encurtador_url.controller.UrlController;
import com.company.tds.encurtador_url.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.dto.response.VisualizarEstatisticasResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlControllerImpl implements UrlController {

    @Override
    public ResponseEntity<CadastrarUrlResponse> cadastrarUrl(CadastrarUrlRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> acessarUrl(String shortUrl) {
        return null;
    }

    @Override
    public ResponseEntity<VisualizarEstatisticasResponse> visualizarEstatisticas(String shortUrl) {
        return null;
    }
}
