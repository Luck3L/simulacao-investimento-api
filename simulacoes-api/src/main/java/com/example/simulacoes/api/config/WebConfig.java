package com.example.simulacoes.api.config;

import com.example.simulacoes.api.interceptor.TelemetryInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TelemetryInterceptor telemetryInterceptor;

    public WebConfig(TelemetryInterceptor telemetryInterceptor) {
        this.telemetryInterceptor = telemetryInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(telemetryInterceptor).addPathPatterns("/**"); 
    }
}