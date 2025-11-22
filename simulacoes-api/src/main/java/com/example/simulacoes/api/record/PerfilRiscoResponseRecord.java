package com.example.simulacoes.api.record;

public record PerfilRiscoResponseRecord(
    Long clienteId,
    String perfil,
    Integer pontuacao,
    String descricao
) {}
