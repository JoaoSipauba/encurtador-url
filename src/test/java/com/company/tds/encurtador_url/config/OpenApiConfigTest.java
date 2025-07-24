package com.company.tds.encurtador_url.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void criarOpenAPIComInformacoesCorretas() {
        OpenApiConfig openApiConfig = new OpenApiConfig("App Teste", "Descrição do App Teste", "1.0.0");
        OpenAPI openAPI = openApiConfig.configuraOpenAPI();

        assertNotNull(openAPI);
        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("App Teste", info.getTitle());
        assertEquals("Descrição do App Teste", info.getDescription());
        assertEquals("1.0.0", info.getVersion());
    }

    @Test
    void criarOpenAPIComValoresNulos() {
        OpenApiConfig openApiConfig = new OpenApiConfig(null, null, null);
        OpenAPI openAPI = openApiConfig.configuraOpenAPI();

        assertNotNull(openAPI);
        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertNull(info.getTitle());
        assertNull(info.getDescription());
        assertNull(info.getVersion());
    }
}