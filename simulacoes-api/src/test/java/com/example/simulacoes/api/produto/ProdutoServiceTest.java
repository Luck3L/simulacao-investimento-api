package com.example.simulacoes.api.produto;

import com.example.simulacoes.api.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto criarProduto(Long id, String nome, Byte idPerfil) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        return produto;
    }
           

    @Test
    void buscarTodos_DeveRetornarListaDeProdutos_QuandoEncontrados() {        
        Produto prod1 = criarProduto(1L, "Produto A", (byte) 1);
        Produto prod2 = criarProduto(2L, "Produto B", (byte) 2);
        List<Produto> produtosMock = Arrays.asList(prod1, prod2);

        when(produtoRepository.findAll()).thenReturn(produtosMock);
        
        List<Produto> resultado = produtoService.buscarTodos();
        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Produto A", resultado.get(0).getNome());
        verify(produtoRepository, times(1)).findAll();
    }
    
    @Test
    void buscarTodos_DeveRetornarListaVazia_QuandoNenhumEncontrado() {        
        when(produtoRepository.findAll()).thenReturn(Collections.emptyList());
        
        List<Produto> resultado = produtoService.buscarTodos();
        
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(produtoRepository, times(1)).findAll();
    }
            
    
    @Test
    void buscarPorId_DeveRetornarOptionalComProduto_QuandoEncontrado() {        
        Long idExistente = 10L;
        Produto produtoMock = criarProduto(idExistente, "Produto Único", (byte) 1);
        
        when(produtoRepository.findById(idExistente)).thenReturn(Optional.of(produtoMock));
        
        Optional<Produto> resultado = produtoService.buscarPorId(idExistente);
        
        assertTrue(resultado.isPresent());
        assertEquals(idExistente, resultado.get().getId());
        verify(produtoRepository, times(1)).findById(idExistente);
    }

    @Test
    void buscarPorId_DeveRetornarOptionalVazio_QuandoNaoEncontrado() {        
        Long idInexistente = 99L;
        
        when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        
        Optional<Produto> resultado = produtoService.buscarPorId(idInexistente);
        
        assertFalse(resultado.isPresent());
        verify(produtoRepository, times(1)).findById(idInexistente);
    }                

    @Test
    void buscarPorIdPerfil_DeveRetornarProdutos_QuandoPerfilPossuiProdutos() {        
        Byte idPerfil = 1;
        Produto prod1 = criarProduto(1L, "Prod Perfil 1", idPerfil);
        Produto prod2 = criarProduto(2L, "Prod Perfil 1", idPerfil);
        List<Produto> produtosDoPerfil = Arrays.asList(prod1, prod2);
        
        when(produtoRepository.findByPerfil_Id(idPerfil)).thenReturn(produtosDoPerfil);
        
        List<Produto> resultado = produtoService.buscarPorIdPerfil(idPerfil);
        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(produtoRepository, times(1)).findByPerfil_Id(idPerfil);
    }

    @Test
    void buscarPorIdPerfil_DeveRetornarListaVazia_QuandoPerfilNaoPossuiProdutos() {        
        Byte idPerfilSemProdutos = 5;
        
        when(produtoRepository.findByPerfil_Id(idPerfilSemProdutos)).thenReturn(Collections.emptyList());
        
        List<Produto> resultado = produtoService.buscarPorIdPerfil(idPerfilSemProdutos);
        
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(produtoRepository, times(1)).findByPerfil_Id(idPerfilSemProdutos);
    }
}