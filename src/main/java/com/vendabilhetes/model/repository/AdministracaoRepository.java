package com.vendabilhetes.model.repository;

import com.vendabilhetes.model.Administracao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdministracaoRepository extends JpaRepository<Administracao, Integer> {
    
    // Buscar Administração por usuário
	Optional<Administracao> findByUsuario_IdUsuario(Integer usuarioId);


    // Listar todas as administrações ordenadas por data de criação
    List<Administracao> findAllByOrderByDataCriacaoDesc();
}
