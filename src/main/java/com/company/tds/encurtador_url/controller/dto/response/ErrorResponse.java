package com.company.tds.encurtador_url.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @Schema(description = "O código do erro.", example = "400")
    private int status;

    @Schema(description = "A mensagem de erro.", example = "Bad Request")
    private String mensagem;
}
