package com.example.simulacoes.api.model; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_telemetria")
public class Telemetria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chamada")
    private Long id;

    @Column(name = "no_servico")
    private String servico;
    
    @Column(name = "ms_resposta_chamada")
    private Long msResposta;
    
    @Column(name = "dt_chamada")
    private LocalDateTime dtChamada;
    
    public Telemetria() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }

    public Long getMsResposta() { return msResposta; }
    public void setMsResposta(Long msResposta) { this.msResposta = msResposta; }

    public LocalDateTime getDtChamada() { return dtChamada; }
    public void setDtChamada(LocalDateTime dtChamada) { this.dtChamada = dtChamada; }
}