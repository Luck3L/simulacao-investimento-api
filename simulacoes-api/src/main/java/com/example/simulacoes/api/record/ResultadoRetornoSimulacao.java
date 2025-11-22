package com.example.simulacoes.api.record;

import java.math.BigDecimal;

public record ResultadoRetornoSimulacao(
		BigDecimal valorFinal,
		BigDecimal rentabilidadeEfetiva,
	    Integer prazoMeses
	    ) {}
