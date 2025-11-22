package com.example.simulacoes.api.exception; // Ajuste o pacote

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) 
public class ClienteNaoEncontradoException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4026748012676157849L;

	public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}