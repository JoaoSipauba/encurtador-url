package com.company.tds.encurtador_url.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisualizarEstatisticasResponse {

    @Schema(description = "Total de acessos à URL encurtada", example = "150")
    private Long totalAccesses;

    @Schema(description = "Média de acessos por dia", example = "5.0")
    private Double averageAccessesPerDay;

}
