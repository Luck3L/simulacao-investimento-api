package com.example.simulacoes.api.recomendacao;

import com.example.simulacoes.api.model.Produto;
import com.example.simulacoes.api.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos-recomendados") // Define o caminho base
public class RecomendacaoController {

    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping("/{idPerfil}") 
    public ResponseEntity<List<Produto>> buscarPorIdPerfil(@PathVariable Byte idPerfil) {
        
        List<Produto> produtos = produtoService.buscarPorIdPerfil(idPerfil);

        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
}