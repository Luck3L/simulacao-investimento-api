package com.example.simulacoes.api.risco;

import com.example.simulacoes.api.record.PerfilRiscoResponseRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfil-risco")
public class PerfilRiscoController {

    private final PerfilRiscoService perfilRiscoService;

    public PerfilRiscoController(PerfilRiscoService perfilRiscoService) {
        this.perfilRiscoService = perfilRiscoService;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<PerfilRiscoResponseRecord> buscarPerfil(
        @PathVariable Long clienteId) {
        
        PerfilRiscoResponseRecord perfil = perfilRiscoService.buscarPerfilPorCliente(clienteId);
        return ResponseEntity.ok(perfil);
    }
}
