package com.example.simulacoes.api.record;

import java.time.LocalDateTime;

public record SimulacaoInvestimentoOutput(
    
    ProdutoValidado produtoValidado,
    
    ResultadoRetornoSimulacao resultadoSimulacao,

    LocalDateTime dataSimulacao
) {}
