package com.example.simulacoes.api.record;

import java.time.LocalDate;

public record PeriodoRecord(
    LocalDate inicio,
    LocalDate fim
) {}