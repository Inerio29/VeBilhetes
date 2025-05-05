package com.vendabilhetes.model.repository;

import java.time.LocalDateTime;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vendabilhetes.model.Bilhete;
import com.vendabilhetes.model.Bilhete.Status;
@Repository
public interface BilheteRepository extends JpaRepository<Bilhete, Long> {

	
// Somatório do valor dos bilhetes vendidos para um promotor
    
	@Query("SELECT SUM(b.evento.ocupacao) FROM Bilhete b WHERE b.evento.usuario.idUsuario = :promotorId")
	Double sumReceitaByPromotor(@Param("promotorId") Integer promotorId);

    
    Optional<Bilhete> findByCodigoBilhete(String codigo);

    List<Bilhete> findByUsuarioIdUsuario(Integer idUsuario);

    long countByStatus(Status status);
    

    
    @Query("SELECT COUNT(b) FROM Bilhete b WHERE b.dataEmissao BETWEEN :inicio AND :fim")
    long countByDataEmissaoBetween(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT COUNT(b) FROM Bilhete b WHERE b.usuario.tipoUsuario = com.vendabilhetes.model.Usuario.TipoUsuario.PROMOTOR")
    long countVendidosPorPromotor();

    // Somatório do valor dos bilhetes vendidos para um promotor



}




