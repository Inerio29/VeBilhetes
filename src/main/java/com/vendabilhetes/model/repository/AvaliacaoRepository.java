package com.vendabilhetes.model.repository;

import com.vendabilhetes.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    List<Avaliacao> findByEventoIdEvento(Integer idEvento);
    List<Avaliacao> findByUsuarioIdUsuario(Integer idUsuario);
}
