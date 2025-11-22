package com.example.simulacoes.api.simulacao;

import com.example.simulacoes.api.model.Simulacao;
import com.example.simulacoes.api.record.SimulacaoInvestimentoInput;
import com.example.simulacoes.api.record.SimulacaoInvestimentoOutput;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @GetMapping("/simulacoes")
    public ResponseEntity<List<Simulacao>> listarTodos() {
        List<Simulacao> simulacoes = simulacaoService.listarTodos();
        return ResponseEntity.ok(simulacoes);
    }

    @GetMapping("simulacoes/por-produto-dia/{dataSimulacao}")
    public ResponseEntity<List<Simulacao>> buscarPorData(@PathVariable("dataSimulacao") String dataSimulacaoStr) {
    	LocalDate dtSimulacao = LocalDate.parse(dataSimulacaoStr);
        List<Simulacao> simulacoes = simulacaoService.buscarPorData(dtSimulacao);
        return ResponseEntity.ok(simulacoes);
    }
    
    @PostMapping("/simular-investimento")
    public ResponseEntity<SimulacaoInvestimentoOutput> simular(
        @RequestBody @Valid SimulacaoInvestimentoInput input) {
        
        SimulacaoInvestimentoOutput output = simulacaoService.simularInvestimento(input);
        
        return ResponseEntity.status(HttpStatus.OK).body(output);
    } 
}