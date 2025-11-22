package com.example.simulacoes.api.simulacao;

import com.example.simulacoes.api.cliente.ClienteService;
import com.example.simulacoes.api.model.Cliente;
import com.example.simulacoes.api.model.Produto;
import com.example.simulacoes.api.model.Simulacao;
import com.example.simulacoes.api.produto.ProdutoRepository;
import com.example.simulacoes.api.record.ProdutoValidado;
import com.example.simulacoes.api.record.ResultadoRetornoSimulacao;
import com.example.simulacoes.api.record.SimulacaoInvestimentoInput;
import com.example.simulacoes.api.record.SimulacaoInvestimentoOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SimulacaoService {

    @Autowired
    private SimulacaoRepository simulacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteService clienteService;

    public List<Simulacao> listarTodos() {
    	List<Simulacao> simulacoes = simulacaoRepository.findAll();
         
         return simulacoes.stream()
        	        .map(this::arredondarValores)
        	        .toList();
    }

    public List<Simulacao> buscarPorData(LocalDate dtSimulacao) {        
        List<Simulacao> simulacoes = simulacaoRepository.findByDtSimulacao(dtSimulacao);
        
        return simulacoes.stream()
       	        .map(this::arredondarValores)
       	        .toList();
    }
    
    @Transactional
    public Simulacao salvar(Simulacao simulacao) {
        return simulacaoRepository.save(simulacao);
    }
    
    public SimulacaoInvestimentoOutput simularInvestimento(SimulacaoInvestimentoInput input) {
    	
    	Cliente clienteInformado = clienteService.validarClienteExiste(input.clienteId());
        
    	Produto produtoEncontrado = produtoRepository.findFirstByNome(input.tipoProduto()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Tipo de Produto '" + input.tipoProduto() + "' não encontrado ou disponível."
            ));
    	
    	BigDecimal rentabilidade = produtoEncontrado.getRentabilidade();
    	
    	BigDecimal DIVISOR_CEM = new BigDecimal("100");

    	BigDecimal rentabilidadeFator = rentabilidade.divide(
    		    DIVISOR_CEM, 
    		    6,
    		    RoundingMode.HALF_UP
    		);
    	
    	MathContext mc = new MathContext(20, RoundingMode.HALF_UP);

    	BigDecimal fatorAcumulado = BigDecimal.ONE.add(rentabilidadeFator)
    	                                     .pow(input.prazoMeses(), mc);

    	BigDecimal valorFinal = input.valor()
    	                             .multiply(fatorAcumulado)
    	                             .setScale(2, RoundingMode.HALF_UP);   

        ProdutoValidado produtoValidado = new ProdutoValidado(
            produtoEncontrado.getId(),
            produtoEncontrado.getNome(),
            produtoEncontrado.getTipo(),
            rentabilidade,
            produtoEncontrado.getRisco()
        );

        ResultadoRetornoSimulacao resultadoSimulacao = new ResultadoRetornoSimulacao(
            valorFinal,
            rentabilidade,
            input.prazoMeses()
        );
        
        SimulacaoInvestimentoOutput simulacaoInvestimentoOutput = new SimulacaoInvestimentoOutput(
                produtoValidado,
                resultadoSimulacao,
                LocalDateTime.now()
            );
        
        gravarSimulacao(clienteInformado, produtoEncontrado, simulacaoInvestimentoOutput, input);

        return simulacaoInvestimentoOutput;
    }
    
    private void gravarSimulacao(Cliente cliente, Produto produto, SimulacaoInvestimentoOutput output, SimulacaoInvestimentoInput input) {        
        Simulacao novaSimulacao = mapToEntity(output, cliente, produto, input); 
        simulacaoRepository.save(novaSimulacao);
    }
    
    private Simulacao mapToEntity(SimulacaoInvestimentoOutput output, Cliente cliente, Produto produto, SimulacaoInvestimentoInput input) {
        Simulacao simulacao = new Simulacao();
        
        simulacao.setCliente(cliente);
        simulacao.setProduto(produto);
        simulacao.setVlInvestido(input.valor());
        simulacao.setVlFinal(output.resultadoSimulacao().valorFinal());
        simulacao.setQtPrazo(output.resultadoSimulacao().prazoMeses());        
        simulacao.setDtSimulacao(LocalDate.now());
        
        return simulacao;
    }
    
    private Simulacao arredondarValores(Simulacao simulacao) {
        if (simulacao.getVlFinal() != null) {
            BigDecimal valorArredondado = simulacao.getVlFinal()
                .setScale(2, RoundingMode.HALF_UP);
            simulacao.setVlFinal(valorArredondado);
        }
        
        if (simulacao.getVlInvestido() != null) {
            BigDecimal valorDesejadoArredondado = simulacao.getVlInvestido()
                .setScale(2, RoundingMode.HALF_UP);
            simulacao.setVlInvestido(valorDesejadoArredondado);
        }

        return simulacao;
    }
}