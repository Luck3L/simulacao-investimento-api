package com.example.simulacoes.api.record;

import java.math.BigDecimal;

public record ProdutoValidado(
    Long id,
    String nome,
    String tipo,
    BigDecimal rentabilidade,
    String risco
) {}