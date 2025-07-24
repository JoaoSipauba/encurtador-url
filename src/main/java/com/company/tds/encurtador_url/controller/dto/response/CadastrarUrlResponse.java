package com.company.tds.encurtador_url.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarUrlResponse {

    @Schema(description = "URL encurtada gerada", example = "http://localhost:8080/abc123")
    private String shortUrl;

}
