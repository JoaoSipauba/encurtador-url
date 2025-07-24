package com.company.tds.encurtador_url.controller.exception;

import com.company.tds.encurtador_url.controller.dto.response.ErrorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @JsonFormat
    ErrorResponse genericException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro não catalogado. Por favor, tente novamente mais tarde.");
    }

    @ExceptionHandler({ErroInternoException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @JsonFormat
    ErrorResponse internalErrorException(final ErroInternoException e) {
        log.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler({RecursoNaoEncontradoException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @JsonFormat
    ErrorResponse notFoundException(final RecursoNaoEncontradoException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @JsonFormat
    ErrorResponse illegalArgumentException(final IllegalArgumentException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "Método HTTP não permitido para este endpoint.");
    }

}
