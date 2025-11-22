package com.example.simulacoes.api.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.simulacoes.api.config.jwt.JwtService;
import com.example.simulacoes.api.config.security.auth.AuthRequest;
import com.example.simulacoes.api.config.security.auth.AuthResponse;

public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtService jwtService;

	@PostMapping("/token")
	public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest request) {

	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(request.username(), request.password())
	    );

	    if (authentication.isAuthenticated()) {
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
	        String token = jwtService.generateToken(userDetails);

	        return ResponseEntity.ok(new AuthResponse(token));
	    }
	    return ResponseEntity.status(401).build();
	}

}