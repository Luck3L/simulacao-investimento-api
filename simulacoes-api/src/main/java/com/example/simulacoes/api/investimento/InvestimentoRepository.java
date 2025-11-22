package com.example.simulacoes.api.investimento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simulacoes.api.model.Investimento;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {
	
    List<Investimento> findByClienteId(Long clienteId);
}