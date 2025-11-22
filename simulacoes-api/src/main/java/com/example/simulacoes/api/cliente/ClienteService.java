package com.example.simulacoes.api.cliente;

import org.springframework.stereotype.Service;

import com.example.simulacoes.api.exception.ClienteNaoEncontradoException;
import com.example.simulacoes.api.model.Cliente;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public Cliente validarClienteExiste(Long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente); 
        
        if (cliente.isEmpty()) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + idCliente + " não foi encontrado.");
        }
        return cliente.get();
    }
}