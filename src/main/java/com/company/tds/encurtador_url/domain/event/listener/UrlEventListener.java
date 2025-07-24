package com.company.tds.encurtador_url.domain.event.listener;

import com.company.tds.encurtador_url.domain.event.UrlAccessedEvent;
import com.company.tds.encurtador_url.domain.useCase.ProcessaUrlAcessadaUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlEventListener {

    private final ProcessaUrlAcessadaUseCase processaUrlAcessadaUseCase;

    @Async
    @EventListener
    public void onUrlAccessedEvent(UrlAccessedEvent event) {
        log.info("Processando UrlAccessedEvent: {}", event);
        processaUrlAcessadaUseCase.executar(event.shortUrl());
    }
}
