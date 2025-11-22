package com.example.simulacoes.api.telemetria;

import com.example.simulacoes.api.record.RelatorioTelemetriaRecord;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/telemetria")
public class TelemetriaController {

    private final TelemetriaService telemetriaService;

    public TelemetriaController(TelemetriaService telemetriaService) {
        this.telemetriaService = telemetriaService;
    }

    @GetMapping
    public ResponseEntity<RelatorioTelemetriaRecord> pesquisarDados() {
        
        RelatorioTelemetriaRecord relatorio = telemetriaService.gerarRelatorio();
        return ResponseEntity.ok(relatorio);
    }
}