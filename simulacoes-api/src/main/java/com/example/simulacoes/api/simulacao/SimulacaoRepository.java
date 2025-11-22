package com.example.simulacoes.api.simulacao;

import com.example.simulacoes.api.model.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {

    List<Simulacao> findByDtSimulacao(LocalDate dtSimulacao);
}