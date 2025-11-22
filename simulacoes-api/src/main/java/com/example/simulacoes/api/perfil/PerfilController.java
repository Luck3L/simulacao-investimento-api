package com.example.simulacoes.api.perfil;

import com.example.simulacoes.api.model.Perfil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> buscarTodosPerfis() {
        List<Perfil> perfis = perfilService.buscarTodos();
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> buscarPerfilPorId(@PathVariable Byte id) {
    	Perfil perfil = perfilService.buscarPorId(id);
        return perfil != null ? ResponseEntity.ok(perfil) : ResponseEntity.notFound().build();
    }
}