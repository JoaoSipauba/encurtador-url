package com.company.tds.encurtador_url.controller.exception;

import com.company.tds.encurtador_url.controller.dto.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerAdviceTest {

    private ExceptionHandlerAdvice exceptionHandlerAdvice;

    @BeforeEach
    void setUp() {
        exceptionHandlerAdvice = new ExceptionHandlerAdvice();
    }

    @Test
    void retornarErrorResponseParaExcecaoGenerica() {
        Exception exception = new Exception("Erro genérico");

        ErrorResponse response = exceptionHandlerAdvice.genericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Erro não catalogado. Por favor, tente novamente mais tarde.", response.getMensagem());
    }

    @Test
    void retornarErrorResponseParaErroInternoException() {
        ErroInternoException exception = new ErroInternoException("Erro interno");

        ErrorResponse response = exceptionHandlerAdvice.internalErrorException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Erro interno", response.getMensagem());
    }

    @Test
    void retornarErrorResponseParaErroInternoExceptionComCausa() {
        Throwable cause = new RuntimeException("Causa original");
        ErroInternoException exception = new ErroInternoException("Erro interno com causa", cause);

        ErrorResponse response = exceptionHandlerAdvice.internalErrorException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertEquals("Erro interno com causa", response.getMensagem());
    }

    @Test
    void retornarErrorResponseParaRecursoNaoEncontradoException() {
        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException("Recurso não encontrado");

        ErrorResponse response = exceptionHandlerAdvice.notFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("Recurso não encontrado", response.getMensagem());
    }

    @Test
    void retornarErrorResponseParaMetodoHttpNaoPermitido() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("POST");

        ErrorResponse response = exceptionHandlerAdvice.methodNotSupportedException(exception);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
        assertEquals("Método HTTP não permitido para este endpoint.", response.getMensagem());
    }

    @Test
    void retornarErrorResponseParaRecursoNaoEncontradoComMensagemPersonalizada() {
        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException("URL encurtada não encontrada");

        ErrorResponse response = exceptionHandlerAdvice.notFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("URL encurtada não encontrada", response.getMensagem());
    }
}