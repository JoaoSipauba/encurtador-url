package com.company.tds.encurtador_url.domain.event.listener;

import com.company.tds.encurtador_url.domain.event.UrlAccessedEvent;
import com.company.tds.encurtador_url.domain.useCase.ProcessaUrlAcessadaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlEventListener {

    private final ProcessaUrlAcessadaUseCase processaUrlAcessadaUseCase;

    @EventListener
    public void onUrlAccessedEvent(UrlAccessedEvent event) {
        processaUrlAcessadaUseCase.executar(event.shortUrl());
    }
}
