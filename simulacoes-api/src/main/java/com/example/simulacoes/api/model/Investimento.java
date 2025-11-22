package com.example.simulacoes.api.model; 

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_investimento")
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_investimento")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Column(name = "vl_investimento")
    private BigDecimal vlInvestimento;

    @Column(name = "dt_investimento")
    private LocalDateTime dtInvestimento;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    
    public BigDecimal getVlInvestimento() { return vlInvestimento; }
    public void setVlInvestimento(BigDecimal vlInvestimento) { this.vlInvestimento = vlInvestimento; }
    
    public LocalDateTime getDtInvestimento() { return dtInvestimento; }
    public void setDtInvestimento(LocalDateTime dtInvestimento) { this.dtInvestimento = dtInvestimento; }
}