package com.example.simulacoes.api.perfil;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.simulacoes.api.exception.ClienteNaoEncontradoException;
import com.example.simulacoes.api.model.Perfil;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public List<Perfil> buscarTodos() {
        return perfilRepository.findAll();
    }
    
    public Perfil buscarPorId(Byte id) {
    	return validarPerfilExiste(id);
    }
    
    public Perfil validarPerfilExiste(Byte id) {
        Optional<Perfil> perfil = perfilRepository.findById(id); 
        
        if (perfil.isEmpty()) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não foi encontrado.");
        }
        return perfil.get();
    }
}