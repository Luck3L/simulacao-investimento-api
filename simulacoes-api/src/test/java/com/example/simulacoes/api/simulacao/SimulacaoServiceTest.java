package com.example.simulacoes.api.simulacao;

import com.example.simulacoes.api.cliente.ClienteService;
import com.example.simulacoes.api.model.Cliente;
import com.example.simulacoes.api.model.Produto;
import com.example.simulacoes.api.model.Simulacao;
import com.example.simulacoes.api.produto.ProdutoRepository;
import com.example.simulacoes.api.record.SimulacaoInvestimentoInput;
import com.example.simulacoes.api.record.SimulacaoInvestimentoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulacaoServiceTest {

    @Mock
    private SimulacaoRepository simulacaoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private SimulacaoService simulacaoService;
        
    private Cliente clienteMock;
    private Produto produtoMock;
    private SimulacaoInvestimentoInput inputMock;

    @BeforeEach
    void setUp() {        
        clienteMock = criarCliente(10L, "Cliente Teste");
        
        produtoMock = new Produto();
        produtoMock.setId(1L);
        produtoMock.setNome("CDB");
        produtoMock.setRentabilidade(new BigDecimal("12.00")); 
        produtoMock.setTipo("RENDA_FIXA");
        produtoMock.setRisco("Médio");

        inputMock = new SimulacaoInvestimentoInput(
            10L,
            new BigDecimal("1000.00"),
        12, 
            "CDB"
        );
    }
            

    @Test
    void simularInvestimento_DeveCalcularCorretamente_EGravar() {                
        when(clienteService.validarClienteExiste(inputMock.clienteId())).thenReturn(clienteMock);        
        when(produtoRepository.findFirstByNome(inputMock.tipoProduto())).thenReturn(Optional.of(produtoMock));        
        when(simulacaoRepository.save(any(Simulacao.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        SimulacaoInvestimentoOutput resultado = simulacaoService.simularInvestimento(inputMock);
                                                                        
        
        BigDecimal valorEsperado = new BigDecimal("3895.98");
        
        assertNotNull(resultado);        
        assertEquals(valorEsperado, resultado.resultadoSimulacao().valorFinal());
                
        verify(simulacaoRepository, times(1)).save(any(Simulacao.class));
        
        ArgumentCaptor<Simulacao> captor = ArgumentCaptor.forClass(Simulacao.class);
        verify(simulacaoRepository).save(captor.capture());
        
        Simulacao simulacaoGravada = captor.getValue();
        assertEquals(clienteMock, simulacaoGravada.getCliente());
        assertEquals(produtoMock, simulacaoGravada.getProduto());
        assertEquals(inputMock.valor(), simulacaoGravada.getVlInvestido());
        assertEquals(valorEsperado, simulacaoGravada.getVlFinal());
        assertEquals(inputMock.prazoMeses(), simulacaoGravada.getQtPrazo());
    }

    @Test
    void simularInvestimento_DeveLancarExcecao_QuandoClienteNaoExiste() {                
        when(clienteService.validarClienteExiste(inputMock.clienteId()))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)); 
        
        assertThrows(ResponseStatusException.class, () -> {
            simulacaoService.simularInvestimento(inputMock);
        });
                
        verify(produtoRepository, never()).findFirstByNome(anyString());
        verify(simulacaoRepository, never()).save(any(Simulacao.class));
    }

    @Test
    void simularInvestimento_DeveLancarExcecao_QuandoProdutoNaoEncontrado() {                
        when(clienteService.validarClienteExiste(inputMock.clienteId())).thenReturn(clienteMock);        
        when(produtoRepository.findFirstByNome(inputMock.tipoProduto())).thenReturn(Optional.empty());
        
        ResponseStatusException excecao = assertThrows(ResponseStatusException.class, () -> {
            simulacaoService.simularInvestimento(inputMock);
        });
        
        assertEquals(HttpStatus.NOT_FOUND, excecao.getStatusCode());
        assertTrue(excecao.getReason().contains("Produto 'CDB' não encontrado"));
                
        verify(simulacaoRepository, never()).save(any(Simulacao.class));
    }
                

    @Test
    void listarTodos_DeveRetornarSimulacoesArredondadas() {        
        Simulacao sim1 = criarSimulacao(1L, clienteMock, produtoMock, new BigDecimal("1000.123"), new BigDecimal("1234.567"), 12, LocalDate.now());
        Simulacao sim2 = criarSimulacao(2L, clienteMock, produtoMock, new BigDecimal("2000.999"), new BigDecimal("3000.000"), 24, LocalDate.now());
        List<Simulacao> simulacoesMock = Arrays.asList(sim1, sim2);

        when(simulacaoRepository.findAll()).thenReturn(simulacoesMock);
        
        List<Simulacao> resultado = simulacaoService.listarTodos();
        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
                
        assertEquals(new BigDecimal("1234.57"), resultado.get(0).getVlFinal());
        assertEquals(new BigDecimal("1000.12"), resultado.get(0).getVlInvestido());
        
        assertEquals(new BigDecimal("3000.00"), resultado.get(1).getVlFinal());
        assertEquals(new BigDecimal("2001.00"), resultado.get(1).getVlInvestido());

        verify(simulacaoRepository, times(1)).findAll();
    }
    
    @Test
    void buscarPorData_DeveRetornarSimulacoesCorretas() {        
        LocalDate dataBusca = LocalDate.of(2025, 11, 21);
        Simulacao sim1 = criarSimulacao(1L, clienteMock, produtoMock, new BigDecimal("1000.00"), new BigDecimal("1100.00"), 12, dataBusca);
        List<Simulacao> simulacoesMock = Collections.singletonList(sim1);

        when(simulacaoRepository.findByDtSimulacao(dataBusca)).thenReturn(simulacoesMock);
        
        List<Simulacao> resultado = simulacaoService.buscarPorData(dataBusca);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(dataBusca, resultado.get(0).getDtSimulacao());
        verify(simulacaoRepository, times(1)).findByDtSimulacao(dataBusca);
    }
                

    @Test
    void salvar_DevePersistirSimulacao() {        
        Simulacao novaSimulacao = criarSimulacao(null, clienteMock, produtoMock, new BigDecimal("500.00"), new BigDecimal("600.00"), 6, LocalDate.now());
        Simulacao simulacaoSalva = criarSimulacao(5L, clienteMock, produtoMock, new BigDecimal("500.00"), new BigDecimal("600.00"), 6, LocalDate.now());

        when(simulacaoRepository.save(novaSimulacao)).thenReturn(simulacaoSalva);
        
        Simulacao resultado = simulacaoService.salvar(novaSimulacao);
        
        assertNotNull(resultado.getId());
        assertEquals(5L, resultado.getId());
        verify(simulacaoRepository, times(1)).save(novaSimulacao);
    }
    
    private Cliente criarCliente(Long id, String nome) {
        Cliente cliente = new Cliente(); 
        cliente.setId(id);
        cliente.setNome(nome);

        return cliente;
    }
    
    private Simulacao criarSimulacao(Long id, Cliente cliente, Produto produto, BigDecimal vlInvestido, BigDecimal vlFinal, Integer qtPrazo, LocalDate dtSimulacao) {
    	Simulacao simulacao = new Simulacao();
    	simulacao.setId(id);
    	simulacao.setCliente(cliente);
    	simulacao.setProduto(produto);
    	simulacao.setVlInvestido(vlInvestido);
    	simulacao.setVlFinal(vlFinal);
    	simulacao.setQtPrazo(qtPrazo);
    	simulacao.setDtSimulacao(dtSimulacao);

        return simulacao;
    }
}