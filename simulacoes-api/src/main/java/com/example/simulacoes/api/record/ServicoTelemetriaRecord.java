package com.example.simulacoes.api.record;

public record ServicoTelemetriaRecord(
 String nome,
 Long quantidadeChamadas,
 Double mediaTempoRespostaMs
) {}