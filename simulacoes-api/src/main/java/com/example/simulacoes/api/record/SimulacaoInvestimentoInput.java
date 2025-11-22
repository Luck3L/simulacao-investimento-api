package com.example.simulacoes.api.record;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SimulacaoInvestimentoInput(
    
    @NotNull Long clienteId,
    
    @NotNull BigDecimal valor,
    
    @NotNull Integer prazoMeses,
    
    @NotNull @Size(min = 1) String tipoProduto
) {}
