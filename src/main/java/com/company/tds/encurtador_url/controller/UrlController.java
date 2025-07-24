package com.company.tds.encurtador_url.controller;

import com.company.tds.encurtador_url.dto.request.CadastrarUrlRequest;
import com.company.tds.encurtador_url.dto.response.CadastrarUrlResponse;
import com.company.tds.encurtador_url.dto.response.VisualizarEstatisticasResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "URL Encurtador", description = "Operações relacionadas ao encurtamento de URLs")
@RequestMapping("/v1/urls")
@Validated
public interface UrlController {
    @Operation(summary = "Cadastra uma nova URL encurtada")
    @ApiResponse(responseCode = "201", description = "URL encurtada criada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CadastrarUrlResponse.class)
    ))
    @PostMapping
    ResponseEntity<CadastrarUrlResponse> cadastrarUrl(@RequestBody @Valid CadastrarUrlRequest request);

    @Operation(summary = "Redireciona para a URL original")
    @ApiResponse(responseCode = "302", description = "Redirecionamento bem sucedido")
    @GetMapping("/{shortUrl}")
    ResponseEntity<Void> acessarUrl(@PathVariable String shortUrl);

    @Operation(summary = "Obtém estatísticas de acesso")
    @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = VisualizarEstatisticasResponse.class)
    ))
    @GetMapping("/{shortUrl}/stats")
    ResponseEntity<VisualizarEstatisticasResponse> visualizarEstatisticas(@PathVariable String shortUrl);
}
