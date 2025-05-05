package com.vendabilhetes.model.repository;

import com.vendabilhetes.model.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    List<Pagamento> findByUsuarioIdUsuario(Integer idUsuario);
}
