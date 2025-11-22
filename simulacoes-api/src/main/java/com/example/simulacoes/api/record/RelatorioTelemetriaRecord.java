package com.example.simulacoes.api.record;

import java.util.List;

public record RelatorioTelemetriaRecord(
 List<ServicoTelemetriaRecord> servicos,
 PeriodoRecord periodo
) {}
