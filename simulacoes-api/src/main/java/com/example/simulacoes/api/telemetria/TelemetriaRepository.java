package com.example.simulacoes.api.telemetria;

import com.example.simulacoes.api.model.Telemetria;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaRepository extends JpaRepository<Telemetria, Long> {
	
	@Query("SELECT MIN(t.dtChamada) FROM Telemetria t")
    LocalDateTime findMinDtChamada();

    @Query("SELECT MAX(t.dtChamada) FROM Telemetria t")
    LocalDateTime findMaxDtChamada();
}