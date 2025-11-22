package com.example.simulacoes.api.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public record InvestimentoResponseRecord(
    Long idInvestimento,
    String nomeProduto,
    String tipoProduto,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    BigDecimal valorInvestimento,
    LocalDateTime dataInvestimento
) {}