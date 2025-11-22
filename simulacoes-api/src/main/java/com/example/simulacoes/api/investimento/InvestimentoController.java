package com.example.simulacoes.api.investimento;

import com.example.simulacoes.api.record.InvestimentoResponseRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/investimentos")
public class InvestimentoController {

    private final InvestimentoService investimentoService;

    public InvestimentoController(InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<InvestimentoResponseRecord>> buscarPorCliente(
        @PathVariable Long clienteId) {
        
        List<InvestimentoResponseRecord> investimentos = investimentoService.buscarInvestimentosPorCliente(clienteId);
        
        if (investimentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(investimentos);
    }
}
