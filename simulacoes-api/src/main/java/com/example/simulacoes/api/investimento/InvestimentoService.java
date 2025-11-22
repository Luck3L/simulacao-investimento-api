package com.example.simulacoes.api.investimento;

import com.example.simulacoes.api.cliente.ClienteService;
import com.example.simulacoes.api.model.Investimento;
import com.example.simulacoes.api.record.InvestimentoResponseRecord;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvestimentoService {

    private final InvestimentoRepository investimentoRepository;
    private final ClienteService clienteService;

    public InvestimentoService(InvestimentoRepository investimentoRepository, ClienteService clienteService) {
        this.investimentoRepository = investimentoRepository;
        this.clienteService = clienteService;
    }

    public List<InvestimentoResponseRecord> buscarInvestimentosPorCliente(Long clienteId) {
        List<Investimento> investimentos = investimentoRepository.findByClienteId(clienteId);
        
        clienteService.validarClienteExiste(clienteId);

        if (investimentos.isEmpty()) {
            return List.of(); 
        }

        return investimentos.stream()
            .map(i -> new InvestimentoResponseRecord(
                i.getId(),
                i.getProduto().getNome(),
                i.getProduto().getTipo(),
                i.getVlInvestimento(),
                i.getDtInvestimento()
            ))
            .toList();
    }
}