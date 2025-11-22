package com.example.simulacoes.api.telemetria;

import org.springframework.stereotype.Service;

import com.example.simulacoes.api.model.Telemetria;
import com.example.simulacoes.api.record.PeriodoRecord;
import com.example.simulacoes.api.record.RelatorioTelemetriaRecord;
import com.example.simulacoes.api.record.ServicoTelemetriaRecord;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TelemetriaService {

    private final TelemetriaRepository telemetriaRepository;

    public TelemetriaService(TelemetriaRepository telemetriaRepository) {
        this.telemetriaRepository = telemetriaRepository;
    }

    public List<Telemetria> pesquisarDados() {
        return telemetriaRepository.findAll();
    }
    
    public Telemetria salvar(Telemetria telemetria) {
        return telemetriaRepository.save(telemetria); 
    }
    
    public RelatorioTelemetriaRecord gerarRelatorio() {
        
        LocalDateTime minDateTime = telemetriaRepository.findMinDtChamada();
        LocalDateTime maxDateTime = telemetriaRepository.findMaxDtChamada();
        
        LocalDate dataInicio = (minDateTime != null) ? minDateTime.toLocalDate() : LocalDate.now();
        LocalDate dataFim = (maxDateTime != null) ? maxDateTime.toLocalDate() : LocalDate.now();

        List<Telemetria> registros = telemetriaRepository.findAll();
        
        Map<String, List<Telemetria>> registrosPorServico = registros.stream()
            .collect(Collectors.groupingBy(Telemetria::getServico));
        
        List<ServicoTelemetriaRecord> servicosAgregados = registrosPorServico.entrySet().stream()
            .map(entry -> {
                String nomeServico = entry.getKey();
                List<Telemetria> listaRegistros = entry.getValue();
                
                Long quantidadeChamadas = (long) listaRegistros.size();
                
                Double mediaTempo = listaRegistros.stream()
                    .collect(Collectors.averagingDouble(Telemetria::getMsResposta));
                
                return new ServicoTelemetriaRecord(
                    nomeServico,
                    quantidadeChamadas,
                    mediaTempo.doubleValue()
                );
            })
            .toList();
        
        PeriodoRecord periodo = new PeriodoRecord(dataInicio, dataFim);
        
        return new RelatorioTelemetriaRecord(servicosAgregados, periodo);
    }
}