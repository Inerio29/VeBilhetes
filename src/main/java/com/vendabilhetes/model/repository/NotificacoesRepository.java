package com.vendabilhetes.model.repository;

import com.vendabilhetes.model.Notificacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacoesRepository extends JpaRepository<Notificacoes, Integer> {
    List<Notificacoes> findByUsuarioIdUsuario(Integer idUsuario);
}
