package com.company.tds.encurtador_url.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CadastrarUrlRequest(
        @NotBlank(message = "A URL original é obrigatória")
        @Schema(description = "URL original a ser encurtada", example = "https://example.com")
        String originalUrl
) {}
