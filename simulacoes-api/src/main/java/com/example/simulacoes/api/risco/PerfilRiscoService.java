package com.example.simulacoes.api.risco;

import com.example.simulacoes.api.model.Cliente;
import com.example.simulacoes.api.model.Investimento;
import com.example.simulacoes.api.model.Perfil;
import com.example.simulacoes.api.perfil.PerfilRepository;
import com.example.simulacoes.api.cliente.ClienteRepository;
import com.example.simulacoes.api.exception.ClienteNaoEncontradoException;
import com.example.simulacoes.api.investimento.InvestimentoRepository;
import com.example.simulacoes.api.record.PerfilRiscoResponseRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PerfilRiscoService {

    private final ClienteRepository clienteRepository;
    private final PerfilRepository perfilRepository;
    private final InvestimentoRepository investimentoRepository;

    public PerfilRiscoService(ClienteRepository clienteRepository, PerfilRepository perfilRepository, InvestimentoRepository investimentoRepository) {
        this.clienteRepository = clienteRepository;
        this.perfilRepository = perfilRepository;
        this.investimentoRepository = investimentoRepository;
    }

    public PerfilRiscoResponseRecord buscarPerfilPorCliente(Long clienteId) {

    	Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + clienteId + " não foi encontrado.");
        }
        Cliente cliente = clienteOpt.get();

        List<Investimento> investimentos = investimentoRepository.findByClienteId(clienteId);
        
        if (investimentos.isEmpty()) {
            return new PerfilRiscoResponseRecord(
                cliente.getId(), "Conservador", 10, "Cliente sem histórico, sugerido perfil conservador."
            );
        }
        
        BigDecimal volumeTotalInvestido = calcularVolumeTotal(investimentos);
        Integer frequenciaMovimentacoes = calcularFrequenciaMedia(investimentos);
        String preferencia = calcularPreferenciaPorLiquidezMedia(investimentos);
        Integer pontuacao = calcularPontuacaoRisco(volumeTotalInvestido, frequenciaMovimentacoes, preferencia);

        String nomePerfil = obterNomePerfil(pontuacao);
        Perfil perfil = obterPerfil(nomePerfil);
        
        return new PerfilRiscoResponseRecord(
            cliente.getId(),
            nomePerfil,
            pontuacao,
            perfil.getDescricao()
        );
    }

    private BigDecimal calcularVolumeTotal(List<Investimento> investimentos) {
        return investimentos.stream()
            .map(Investimento::getVlInvestimento)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private Integer calcularFrequenciaMedia(List<Investimento> investimentos) {
        if (investimentos.size() <= 1) return 1;

        LocalDateTime dataMaisAntiga = investimentos.stream()
            .map(Investimento::getDtInvestimento)
            .min(LocalDateTime::compareTo)
            .orElse(LocalDateTime.now());

        LocalDateTime dataMaisRecente = investimentos.stream()
            .map(Investimento::getDtInvestimento)
            .max(LocalDateTime::compareTo)
            .orElse(LocalDateTime.now());
            
        long mesesTotais = ChronoUnit.MONTHS.between(dataMaisAntiga, dataMaisRecente);
        if (mesesTotais < 1) mesesTotais = 1;
        
        double frequenciaMes = (double) investimentos.size() / mesesTotais;

        return (int) Math.round(frequenciaMes);
    }
    
	private String calcularPreferenciaPorLiquidezMedia(List<Investimento> investimentos) {
        double mediaLiquidez = investimentos.stream()
            .map(i -> i.getProduto().getClassificacaoLiquidez()) 
            .filter(score -> score != null)
            .mapToDouble(Short::doubleValue)
            .average()
            .orElse(5.0);
        
        if (mediaLiquidez <= 3.0) {
            return "LIQUIDEZ";
        } else {
            return "RENTABILIDADE";
        }
    }
	
	public Integer calcularPontuacaoRisco(
        BigDecimal volumeTotalInvestido,
        Integer frequenciaMovimentacoes,
        String preferencia) {

        int pontuacaoVolume = calcularPontuacaoPorVolume(volumeTotalInvestido);
        int pontuacaoFrequencia = calcularPontuacaoPorFrequencia(frequenciaMovimentacoes);
        int pontuacaoPreferencia = calcularPontuacaoPorPreferencia(preferencia);

        int pontuacaoTotal = pontuacaoVolume + pontuacaoFrequencia + pontuacaoPreferencia;
        
        return Math.min(100, Math.max(0, pontuacaoTotal)); 
    }
    private int calcularPontuacaoPorVolume(BigDecimal volume) {
        if (volume.compareTo(new BigDecimal("100000.00")) > 0) {
            return 40;
        } else if (volume.compareTo(new BigDecimal("25000.00")) > 0) {
            return 20;
        } else {
            return 5;
        }
    }
    
    private int calcularPontuacaoPorFrequencia(Integer frequencia) {
        if (frequencia >= 4) {
            return 35;
        } else if (frequencia >= 1) {
            return 15;
        } else {
            return 5;
        }
    }
    
    private int calcularPontuacaoPorPreferencia(String preferencia) {
        if ("RENTABILIDADE".equalsIgnoreCase(preferencia)) {
            return 25;
        } else if ("LIQUIDEZ".equalsIgnoreCase(preferencia)) {
            return 10;
        } else {
            return 5; 
        }
    }
    
    private String obterNomePerfil(Integer pontuacao) {
        if (pontuacao == null) return "Não Classificado";
        if (pontuacao <= 30) return "Conservador";
        if (pontuacao <= 70) return "Moderado";
        return "Agressivo";
    }
    private Perfil obterPerfil(String perfil) {
        return perfilRepository.findByNome(perfil);
    }
}
