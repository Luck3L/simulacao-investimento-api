package com.example.simulacoes.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_simulacao")
@Getter 
@Setter
@ToString(exclude = {"cliente", "produto"})
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simulacao")
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Column(name = "vl_investido", nullable = false, precision = 19, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal vlInvestido;

    @Column(name = "vl_final", precision = 19, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private BigDecimal vlFinal;

    @Column(name = "qt_prazo", nullable = false)
    private Integer qtPrazo;

    @Column(name = "dt_simulacao", nullable = false)
    private LocalDate dtSimulacao;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;
        
        Simulacao simulacao = (Simulacao) o;
        return id.equals(simulacao.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 31;
    }
}