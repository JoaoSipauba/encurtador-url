package com.company.tds.encurtador_url.controller.exception;

public class ErroInternoException extends RuntimeException {
    public ErroInternoException(String msg) {
        super(msg);
    }

    public ErroInternoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

