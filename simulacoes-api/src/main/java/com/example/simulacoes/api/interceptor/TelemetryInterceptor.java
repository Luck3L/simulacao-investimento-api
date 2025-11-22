package com.example.simulacoes.api.interceptor;

import com.example.simulacoes.api.model.Telemetria;
import com.example.simulacoes.api.telemetria.TelemetriaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.time.LocalDateTime;

@Component
public class TelemetryInterceptor implements HandlerInterceptor {

    private final TelemetriaService telemetriaService;
    
    private static final String START_TIME_ATTRIBUTE = "startTime"; 

    public TelemetryInterceptor(TelemetriaService telemetriaService) {
        this.telemetriaService = telemetriaService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTRIBUTE, System.nanoTime()); 
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        if (startTime == null) {
            return; 
        }
        long durationNanos = System.nanoTime() - startTime;

        String endpoint = request.getRequestURI();
        LocalDateTime dataRegistro = LocalDateTime.now();

        Telemetria registro = new Telemetria();  
        
        registro.setServico(endpoint);
        registro.setMsResposta((long) (durationNanos / 1_000_000.0));
        registro.setDtChamada(dataRegistro); 
        
        telemetriaService.salvar(registro);
    }
}