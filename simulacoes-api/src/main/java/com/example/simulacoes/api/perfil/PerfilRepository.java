package com.example.simulacoes.api.perfil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simulacoes.api.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Byte> {
	
	Perfil findByNome(String nome);
    
}