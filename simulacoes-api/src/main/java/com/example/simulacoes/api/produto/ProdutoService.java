package com.example.simulacoes.api.produto;

import com.example.simulacoes.api.model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }
    
    public List<Produto> buscarPorIdPerfil(Byte idPerfil) {
        return produtoRepository.findByPerfil_Id(idPerfil);
    }
    
}