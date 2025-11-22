package com.example.simulacoes.api.produto;

import com.example.simulacoes.api.model.Produto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findByPerfil_Id(Byte perfil);
	
	Optional<Produto> findFirstByNome(String nome);
}