package com.example.simulacoes.api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @Column(name = "no_produto", nullable = false, length = 50)
    private String nome;

    @Column(name = "tp_produto", nullable = false, length = 50)
    private String tipo; 

    @Column(name = "vl_rentabilidade", nullable = false, precision = 18, scale = 4)
    private BigDecimal rentabilidade;

    @Column(name = "ds_risco", nullable = false, length = 20)
    private String risco; 

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;
    
    @Column(name = "vl_classificacao_liquidez")
    private Short classificacaoLiquidez;
    
    public Produto() {}
    
    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTipo() {
        return this.tipo;
    }
    
    public BigDecimal getRentabilidade() {
        return this.rentabilidade;
    }
    
    public String getRisco() {
        return this.risco;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void setRentabilidade(BigDecimal rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }
    
    public Short getClassificacaoLiquidez() {
    	return classificacaoLiquidez; 
    }
    public void setClassificacaoLiquidez(Short classificacaoLiquidez) {
    	this.classificacaoLiquidez = classificacaoLiquidez; 
    }
    
}