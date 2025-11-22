package com.example.simulacoes.api.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_perfil")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Perfil {

    @Id
    @Column(name = "id_perfil")
    @EqualsAndHashCode.Include
    private Byte id;

    @Column(name = "no_perfil", nullable = false, length = 20)
    private String nome;
    
    @Column(name = "ds_perfil", nullable = false, length = 100)
    private String descricao;    
    
}
