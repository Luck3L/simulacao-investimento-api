package com.example.simulacoes.api.cliente;

import com.example.simulacoes.api.exception.ClienteNaoEncontradoException;
import com.example.simulacoes.api.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private final Cliente cliente1 = criarCliente(1L, "João Silva");
    private final Cliente cliente2 = criarCliente(2L, "Maria Souza");

    @Test
    void buscarTodos_DeveRetornarListaDeClientes() {
        List<Cliente> clientesMock = Arrays.asList(cliente1, cliente2);
        
        when(clienteRepository.findAll()).thenReturn(clientesMock);

        List<Cliente> resultado = clienteService.buscarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(clienteRepository, times(1)).findAll(); 
    }


    @Test
    void salvar_DeveRetornarClienteSalvoComID() {
        Cliente clienteParaSalvar = criarCliente(null, "Novo Cliente");
        Cliente clienteSalvo = criarCliente(3L, "Novo Cliente");

        when(clienteRepository.save(clienteParaSalvar)).thenReturn(clienteSalvo);

        Cliente resultado = clienteService.salvar(clienteParaSalvar);

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Novo Cliente", resultado.getNome());
        
        verify(clienteRepository, times(1)).save(clienteParaSalvar);
    }
    
    @Test
    void validarClienteExiste_DeveRetornarCliente_QuandoEncontrado() {
        Long idExistente = 1L;
        when(clienteRepository.findById(idExistente)).thenReturn(Optional.of(cliente1));

        Cliente resultado = clienteService.validarClienteExiste(idExistente);

        assertNotNull(resultado);
        assertEquals(idExistente, resultado.getId());
        verify(clienteRepository, times(1)).findById(idExistente);
    }
    
    @Test
    void validarClienteExiste_DeveLancarExcecao_QuandoNaoEncontrado() {
        Long idInexistente = 99L;

        when(clienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Exception excecao = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.validarClienteExiste(idInexistente);
        });

        String mensagemEsperada = "Cliente com ID " + idInexistente + " não foi encontrado.";
        assertTrue(excecao.getMessage().contains(mensagemEsperada));
        
        verify(clienteRepository, times(1)).findById(idInexistente);
    }
    
    private Cliente criarCliente(Long id, String nome) {
        Cliente cliente = new Cliente(); 
        cliente.setId(id);
        cliente.setNome(nome);

        return cliente;
    }
}